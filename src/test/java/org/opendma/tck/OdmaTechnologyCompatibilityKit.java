package org.opendma.tck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.opendma.api.*;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;

public class OdmaTechnologyCompatibilityKit {
	
	public static String debugDescribe(OdmaObject obj) {
		String result = obj instanceof OdmaClass ? "class(id=`" : obj instanceof OdmaPropertyInfo ? "property(id=`" : "object(id=`";
		try {
			OdmaId id = obj.getId();
			result = result + (id == null ? "<null>" : id.toString());
		} catch(Exception e) {
			result = result + "<except>";
		}
		if(obj instanceof OdmaClass) {
			result = result + "`, qname=`";
			try {
				OdmaQName qname = ((OdmaClass)obj).getQName();
				result = result + (qname == null ? "<null>" : qname.toString());
			} catch(Exception e) {
				result = result + "<except>";
			}
		} else if(obj instanceof OdmaPropertyInfo) {
			result = result + "`, qname=`";
			try {
				OdmaQName qname = ((OdmaPropertyInfo)obj).getQName();
				result = result + (qname == null ? "<null>" : qname.toString());
			} catch(Exception e) {
				result = result + "<except>";
			}
		}
		result = result + "`, class=`";
		try {
			OdmaClass clazz = obj.getOdmaClass();
			if(clazz == null) {
				result = result + "<null>";
			} else {
				try {
					OdmaQName qname = clazz.getQName();
					result = result + (qname == null ? "<qname-null>" : qname.toString());
				} catch(Exception e) {
					result = result + "<qname-except>";
				}
			}
		} catch(Exception e) {
			result = result + "<except>";
		}
		result = result + "`)";
		return result;
	}

    public static List<String> verifyObjectBaseline(OdmaObject obj) {
        return verifyObjectBaseline(obj, new HashSet<OdmaId>(), new HashSet<OdmaQName>());
    }

    public static List<String> verifyObjectBaseline(OdmaObject obj, HashSet<OdmaId> objectLoopCheck, HashSet<OdmaQName> classLoopCheck) {
        LinkedList<String> result = new LinkedList<>();
        if(obj.getId() == null) {
            result.add(debugDescribe(obj)+".getId() returns null");
        }
        if(objectLoopCheck.contains(obj.getId())) {
            return result;
        }
        objectLoopCheck.add(obj.getId());
        if(obj.getGuid() == null) {
            result.add(debugDescribe(obj)+".getGuid() returns null");
        }
        if(obj.getRepository() == null) {
            result.add(debugDescribe(obj)+".getRepository() returns null");
        }
        if(obj.getOdmaClass() == null) {
            result.add(debugDescribe(obj)+".getOdmaClass() returns null");
            result.add(debugDescribe(obj)+" ABORT baseline verification");
            return result;
        }
        if(obj.getOdmaClass().getProperties() == null) {
            result.add(debugDescribe(obj)+".getOdmaClass().getProperties() returns null");
            result.add(debugDescribe(obj)+" ABORT baseline verification");
            return result;
        }
        if(obj.getOdmaClass().isInstantiable() == false) {
            result.add(debugDescribe(obj)+".getOdmaClass().isInstantiable() == false");
        }
        if(obj.getOdmaClass().isAspect() == true) {
            result.add(debugDescribe(obj)+".getOdmaClass().isAspect() == true");
        }
        // check we have all properties
        OdmaClass clazz = obj.getOdmaClass();
        for(OdmaPropertyInfo pi : clazz.getProperties()) {
            OdmaProperty prop;
            try
            {
                prop = obj.getProperty(pi.getQName());
            }
            catch (OdmaPropertyNotFoundException e)
            {
                result.add(debugDescribe(obj)+": Missing property `"+pi.getQName()+"`");
                continue;
            }
            if(prop.getType() != OdmaType.fromNumericId(pi.getDataType())) {
                result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` has a different data type than described in the property info object");
            }
            if(prop.isMultiValue() != pi.isMultiValue()) {
                result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` has a different cardinality than described in the property info object");
            }
            if(pi.isRequired() && prop.getValue() == null) {
                result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` is required but has null value");
            }
            if(pi.isMultiValue() && prop.getValue() == null) {
                result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` is MultiValue but has null value");
            }
            if(pi.isMultiValue() && pi.isRequired()) {
                try {
                    if(prop.getType() == OdmaType.REFERENCE) {
                        if(!prop.getReferenceIterable().iterator().hasNext()) {
                            result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` is required but reference set does not have any elements");
                        }
                    } else {
                        List<?> list = (List<?>)prop.getValue();
                        if(list.isEmpty()) {
                            result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` is required but list does not have any elements");
                        }
                    }
                } catch(OdmaInvalidDataTypeException e) {
                    result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` caused OdmaInvalidDataTypeException");
                } catch(ClassCastException e) {
                    result.add(debugDescribe(obj)+": Property `"+pi.getQName()+"` caused ClassCastException");
                }
            }
        }
        // if available properties are marked as complete, check all properties are available
        if(obj.availablePropertiesComplete()) {
            HashSet<OdmaQName> availablePropertyNames = new HashSet<OdmaQName>();
            Iterator<OdmaProperty> itAvailableProperties = obj.availableProperties();
            while(itAvailableProperties.hasNext()) {
                availablePropertyNames.add(itAvailableProperties.next().getName());
            }
            for(OdmaPropertyInfo pi : clazz.getProperties()) {
                if(!availablePropertyNames.contains(pi.getQName())) {
                    result.add(debugDescribe(obj)+": Marked as availablePropertiesComplete, but property is not available `"+pi.getQName()+"`");
                }
            }
        }
        // verify class of this object
        result.addAll(verifyClassBaseline(obj.getOdmaClass(), objectLoopCheck, classLoopCheck));
        return result;
    }

    public static List<String> verifyClassBaseline(OdmaClass cls) {
        return verifyClassBaseline(cls, new HashSet<OdmaId>(), new HashSet<OdmaQName>());
    }

    public static List<String> verifyClassBaseline(OdmaClass cls, HashSet<OdmaId> objectLoopCheck, HashSet<OdmaQName> classLoopCheck) {
        List<String> result = verifyObjectBaseline(cls, objectLoopCheck, classLoopCheck);
        if(!result.isEmpty()) {
            result.add(debugDescribe(cls)+": Object baseline test failed");
            result.add(debugDescribe(cls)+" ABORT class baseline verification");
            return result;
            
        }
        if(cls.getName() == null) {
            result.add(debugDescribe(cls)+".getName() returns null");
        }
        if(cls.getNamespace() == null) {
            result.add(debugDescribe(cls)+".getNamespace() returns null");
        }
        if(cls.getQName() == null) {
            result.add(debugDescribe(cls)+".getQName() returns null");
            result.add(debugDescribe(cls)+" ABORT class baseline verification");
            return result;
        }
        if(classLoopCheck.contains(cls.getQName())) {
            return result;
        }
        classLoopCheck.add(cls.getQName());
        if(cls.getProperties() == null) {
            result.add(debugDescribe(cls)+".getProperties() returns null");
            result.add(debugDescribe(cls)+" ABORT class baseline verification");
            return result;
        }
        if(cls.getProperties().iterator().hasNext() == false) {
            result.add(debugDescribe(cls)+".getProperties().iterator().hasNext() == false");
            result.add(debugDescribe(cls)+" ABORT class baseline verification");
            return result;
        }
        if(cls.getDeclaredProperties() == null) {
            result.add(debugDescribe(cls)+".getDeclaredProperties() returns null");
            result.add(debugDescribe(cls)+" ABORT class baseline verification");
            return result;
        }
        if(cls.getAspects() == null) {
            result.add(debugDescribe(cls)+".getAspects() returns null");
        }
        if(cls.getSuperClass() != null) {
            if(cls.isAspect() != cls.getSuperClass().isAspect()) {
                result.add(debugDescribe(cls)+".isAspect() != cls.getSuperClass().isAspect()");
            }
            if(cls.getSuperClass().getSubClasses() == null) {
                result.add(debugDescribe(cls)+".getSuperClass().getSubClasses() == null");
            }  else {
                boolean found = false;
                for(OdmaClass superSubClass : cls.getSuperClass().getSubClasses()) {
                    if(superSubClass.getQName().equals(cls.getQName())) {
                        found = true;
                    }
                }
                if(!found) {
                    result.add(debugDescribe(cls)+".getSuperClass().getSubClasses() does not contain cls");
                }
            }
            HashSet<OdmaQName> classHierarchyLoopCheck = new HashSet<OdmaQName>();
            classHierarchyLoopCheck.add(cls.getQName());
            OdmaClass s = cls.getSuperClass();
            String loop = cls.getQName().toString();
            while(s != null) {
                loop = loop+"->"+s.getQName();
                if(classHierarchyLoopCheck.contains(s.getQName())) {
                    result.add(debugDescribe(cls)+": Class HierarchyLoopCheck failed. Loop: "+loop);
                    break;
                }
                s = s.getSuperClass();
            }
        }
        if(cls.getSubClasses() == null) {
            result.add(debugDescribe(cls)+".getSubClasses() == null");
        } else {
            for(OdmaClass subClass : cls.getSubClasses()) {
                if(subClass.getSuperClass() == null) {
                    result.add(debugDescribe(cls)+".getSubClasses()[].getSuperClass() == null for cls.getSubClasses()["+debugDescribe(subClass)+"]");
                } else if(!subClass.getSuperClass().getQName().equals(cls.getQName())) {
                    result.add(debugDescribe(cls)+".getSubClasses()[].getSuperClass().getQName() != cls.getQName() for cls.getSubClasses()["+debugDescribe(subClass)+"]");
                }
            }
        }
        HashMap<OdmaQName, OdmaPropertyInfo> superProps = new HashMap<OdmaQName, OdmaPropertyInfo>();
        if(cls.getSuperClass() != null) {
            for(OdmaPropertyInfo pi : cls.getSuperClass().getProperties()) {
                if(pi.getQName() == null) {
                    result.add(debugDescribe(cls.getSuperClass())+" contains "+debugDescribe(pi)+" with getQName()==null");
                    continue;
                }
                if(superProps.containsKey(pi.getQName())) {
                    result.add(debugDescribe(cls.getSuperClass())+" contains multiple property infos with duplicate QName "+pi.getQName());
                    continue;
                }
                superProps.put(pi.getQName(), pi);
            }
        }
        if(cls.isAspect()) {
            if(cls.getAspects().iterator().hasNext()) {
                result.add(debugDescribe(cls)+" is declared as Aspect but does have aspects itself");
            }
        }
        HashSet<OdmaQName> superClassAspectNames = new HashSet<OdmaQName>();
        if(cls.getSuperClass() != null) {
            for(OdmaClass superAspect : cls.getSuperClass().getAspects()) {
                superClassAspectNames.add(superAspect.getQName());
            }
        }
        HashMap<OdmaQName, OdmaPropertyInfo> aspectProps = new HashMap<OdmaQName, OdmaPropertyInfo>();
        HashSet<OdmaQName> aspectNames = new HashSet<OdmaQName>();
        for(OdmaClass aspect : cls.getAspects()) {
            if(!aspect.isAspect()) {
                result.add(debugDescribe(cls)+" aspect "+debugDescribe(aspect)+" has isAspect()==false");
            }
            if(!aspect.isInstantiable()) {
                result.add(debugDescribe(cls)+" aspect "+debugDescribe(aspect)+" has isInstantiable()==false");
            }
            aspectNames.add(aspect.getQName());
            if(!superClassAspectNames.contains(aspect.getQName())) {
                boolean found = false;
                for(OdmaClass aspectSubClass : aspect.getSubClasses()) {
                    if(aspectSubClass.getQName().equals(cls.getQName())) {
                        found = true;
                    }
                }
                if(!found) {
                    result.add(debugDescribe(cls)+" declared aspect "+debugDescribe(aspect)+" does not list cls as sub class");
                }
            }
            for(OdmaPropertyInfo pi : aspect.getProperties()) {
                if(pi.getQName() == null) {
                    result.add(debugDescribe(cls)+" aspect "+debugDescribe(aspect)+" contains property info with getQName()==null");
                    continue;
                }
                if(aspectProps.containsKey(pi.getQName())) {
                    result.add(debugDescribe(cls)+ " imports multiple properties with same name through aspects: "+pi.getQName());
                    continue;
                }
                boolean aspectPropInherited;
                if(superClassAspectNames.contains(aspect.getQName())) {
                	aspectPropInherited = true;
                } else {
                	aspectPropInherited = false;
                    if(cls.getSuperClass() != null) {
                        for(OdmaClass superAspect : cls.getSuperClass().getAspects()) {
                            if(isOrExtends(superAspect.getQName(), aspect)) {
                                for(OdmaPropertyInfo superAspectProp : superAspect.getProperties()) {
                                    if(superAspectProp.getQName().equals(pi.getQName())) {
                                        aspectPropInherited = true;
                                    }
                                }
                            }
                        }
                    }
                	for(OdmaQName superAspectName : superClassAspectNames) {
                		if(isOrExtends(superAspectName, aspect)) {
                			aspectPropInherited = true;
                		}
                	}
                }
                if(!aspectPropInherited && superProps.containsKey(pi.getQName())) {
                    result.add(debugDescribe(cls)+" imports aspect "+debugDescribe(aspect)+" with naming conflict of property "+debugDescribe(pi)+" with inherited properties from super class");
                }
                aspectProps.put(pi.getQName(), pi);
            }
        }
        HashMap<OdmaQName, OdmaPropertyInfo> allProps = new HashMap<OdmaQName, OdmaPropertyInfo>();
        for(OdmaPropertyInfo pi : cls.getProperties()) {
            if(pi.getQName() == null) {
                result.add(debugDescribe(cls)+" contains "+debugDescribe(pi)+" with getQName()==null");
                continue;
            }
            if(allProps.containsKey(pi.getQName())) {
                result.add(debugDescribe(cls)+" contains multiple property infos with duplicate QName "+pi.getQName());
                continue;
            }
            allProps.put(pi.getQName(), pi);
        }
        HashMap<OdmaQName, OdmaPropertyInfo> declaredProps = new HashMap<OdmaQName, OdmaPropertyInfo>();
        for(OdmaPropertyInfo pi : cls.getDeclaredProperties()) {
            if(pi.getQName() == null) {
                result.add(debugDescribe(cls)+" contains declared "+debugDescribe(pi)+" with getQName()==null");
                continue;
            }
            if(declaredProps.containsKey(pi.getQName())) {
                result.add(debugDescribe(cls)+" contains multiple declared property infos with duplicate QName "+pi.getQName());
                continue;
            }
            declaredProps.put(pi.getQName(), pi);
            if(superProps.containsKey(pi.getQName())) {
                if(pi.getDataType() == OdmaType.REFERENCE.getNumericId()) {
                    String piDiff = propInfoDiff(pi,superProps.get(pi.getQName()),inheritedPropertiesStrictMatch,inheritedPropertiesReferenceMatch==REFERENCE_MATCH_LEVEL.EXACT);
                    if(piDiff != null) {
                        result.add(debugDescribe(cls)+" declared property "+debugDescribe(pi)+" conflicts with inherited properties from superclass. Both are reference but have different specs: "+piDiff);
                    }
                    if(inheritedPropertiesReferenceMatch==REFERENCE_MATCH_LEVEL.ISOREXTENDS) {
                        if(!isOrExtends(superProps.get(pi.getQName()).getReferenceClass().getQName(),pi.getReferenceClass())) {
                            result.add(debugDescribe(cls)+" declared property "+debugDescribe(pi)+" conflicts with inherited properties from superclass. Both are reference but reference class of declared prop does not extend reference class of super prop: "+pi.getReferenceClass().getQName()+" is not and does not extend "+superProps.get(pi.getQName()).getReferenceClass().getQName());
                        }
                    }
                } else {
                    result.add(debugDescribe(cls)+" declares property "+debugDescribe(pi)+" with naming conflict with inherited properties from super class");
                }
            }
            if(aspectProps.containsKey(pi.getQName())) {
                result.add(debugDescribe(cls)+" declares property "+debugDescribe(pi)+" with naming conflict with inherited properties from aspects");
            }
            if(!allProps.containsKey(pi.getQName())) {
                result.add(debugDescribe(cls)+" declared property "+debugDescribe(pi)+" is missing in getProperties()");
            } else {
                String piDiff = propInfoDiff(pi,allProps.get(pi.getQName()),true,true);
                if(piDiff != null) {
                    result.add(debugDescribe(cls)+" declared property "+debugDescribe(pi)+" is different in getProperties(): "+piDiff);
                }
            }
        }
        for(OdmaPropertyInfo pi : superProps.values()) {
            if(!allProps.containsKey(pi.getQName())) {
                result.add(debugDescribe(cls)+" inherited superclass property "+debugDescribe(pi)+" not part of properties");
            } else {
                String piDiff = propInfoDiff(pi,allProps.get(pi.getQName()),inheritedPropertiesStrictMatch,inheritedPropertiesReferenceMatch==REFERENCE_MATCH_LEVEL.EXACT); 
                if(piDiff != null) {
                    result.add(debugDescribe(cls)+" inherited superclass property "+debugDescribe(pi)+" is different in getProperties(): "+piDiff);
                }
                if(pi.getDataType() == OdmaType.REFERENCE.getNumericId() && inheritedPropertiesReferenceMatch==REFERENCE_MATCH_LEVEL.ISOREXTENDS) {
                    if(!isOrExtends(pi.getReferenceClass().getQName(),allProps.get(pi.getQName()).getReferenceClass())) {
                        result.add(debugDescribe(cls)+" inherited superclass property "+debugDescribe(pi)+" is different in getProperties(): Both are reference but reference class of property in getProperties() does not extend reference class of super prop: "+allProps.get(pi.getQName()).getReferenceClass().getQName()+" is not and does not extend "+pi.getReferenceClass().getQName());
                    }
                }
            }
        }
        for(OdmaPropertyInfo pi : aspectProps.values()) {
            if(!allProps.containsKey(pi.getQName())) {
                result.add(debugDescribe(cls)+" inherited aspect property "+debugDescribe(pi)+" not part of properties");
            } else {
                String piDiff = propInfoDiff(pi,allProps.get(pi.getQName()),inheritedPropertiesStrictMatch,inheritedPropertiesReferenceMatch==REFERENCE_MATCH_LEVEL.EXACT); 
                if(piDiff != null) {
                    result.add(debugDescribe(cls)+" inherited aspect property "+debugDescribe(pi)+" is different in getProperties(): "+piDiff);
                }
            }
        }
        for(OdmaQName pn : allProps.keySet()) {
            if( !superProps.containsKey(pn) && !aspectProps.containsKey(pn) && !declaredProps.containsKey(pn) ) {
                result.add(debugDescribe(cls)+" property "+pn+" is neither inherited through super class, inherited through aspects or declared");
            }
        }
        if(cls.getSuperClass() != null) {
            verifyClassBaseline(cls.getSuperClass(), objectLoopCheck, classLoopCheck);            
        }
        for(OdmaClass aspect : cls.getAspects()) {
            verifyClassBaseline(aspect, objectLoopCheck, classLoopCheck);            
        }
        if(cls.getSuperClass() != null) {
            for(OdmaClass superAspect : cls.getSuperClass().getAspects()) {
                if(!aspectNames.contains(superAspect.getQName())) {
                	boolean foundExtendingAspect = false;
                    for(OdmaClass a : cls.getAspects()) {
                    	foundExtendingAspect |= isOrExtends(superAspect.getQName(), a);
                    }
                	if(!foundExtendingAspect) {
                        result.add(debugDescribe(cls)+" inherited aspect "+debugDescribe(superAspect)+" from super class "+debugDescribe(cls.getSuperClass())+" but is missing in aspects");
                	}
                }
            }
        }
        return result;
    }
    
    public static enum REFERENCE_MATCH_LEVEL { EXACT, ISOREXTENDS, ANY };
    
    private static boolean inheritedPropertiesStrictMatch = true;
    
    private static REFERENCE_MATCH_LEVEL inheritedPropertiesReferenceMatch = REFERENCE_MATCH_LEVEL.ISOREXTENDS;
    
    public static void setInheritedPropertiesStrictMatch(boolean strict) {
    	inheritedPropertiesStrictMatch = strict;
    }
    
    public static void setInheritedPropertiesReferenceMatch(REFERENCE_MATCH_LEVEL matchLevel) {
    	inheritedPropertiesReferenceMatch = matchLevel;
    }

    private static String propInfoDiff(OdmaPropertyInfo piA, OdmaPropertyInfo piB, boolean strictMatch, boolean referenceClassMatch) {
        if(!piA.getName().equals(piB.getName())) {
            return "Name is different: `"+piA.getName()+"` and `"+piB.getName()+"`";
        }
        if(!piA.getNamespace().equals(piB.getNamespace())) {
            return "Namespace is different: `"+piA.getNamespace()+"` and `"+piB.getNamespace()+"`";
        }
        if(!piA.getQName().equals(piB.getQName())) {
            return "QName is different: `"+piA.getQName()+"` and `"+piB.getQName()+"`";
        }
        if(!piA.getDataType().equals(piB.getDataType())) {
            return "DataType is different: `"+piA.getDataType()+"` and `"+piB.getDataType()+"`";
        }
        if(!piA.isMultiValue().equals(piB.isMultiValue())) {
            return "MultiValue is different: `"+piA.isMultiValue()+"` and `"+piB.isMultiValue()+"`";
        }
        if(strictMatch) {
            if(!piA.getDisplayName().equals(piB.getDisplayName())) {
                return "DisplayName is different: `"+piA.getDisplayName()+"` and `"+piB.getDisplayName()+"`";
            }
            if(!piA.isRequired().equals(piB.isRequired())) {
                return "Required is different: `"+piA.isRequired()+"` and `"+piB.isRequired()+"`";
            }
            if(!piA.isReadOnly().equals(piB.isReadOnly())) {
                return "ReadOnly is different: `"+piA.isReadOnly()+"` and `"+piB.isReadOnly()+"`";
            }
            if(!piA.isHidden().equals(piB.isHidden())) {
                return "Hidden is different: `"+piA.isHidden()+"` and `"+piB.isHidden()+"`";
            }
            if(!piA.isSystem().equals(piB.isSystem())) {
                return "System is different: `"+piA.isSystem()+"` and `"+piB.isSystem()+"`";
            }
        }
        if(!(piA.getChoices().iterator().hasNext() == piB.getChoices().iterator().hasNext())) {
            return "Choices presence is different: `"+piA.getChoices().iterator().hasNext()+"` and `"+piB.getChoices().iterator().hasNext()+"`";
        }
        if(piA.getChoices().iterator().hasNext()) {
            HashSet<OdmaId> choices = new HashSet<OdmaId>();
            for(OdmaChoiceValue cv : piA.getChoices()) {
                choices.add(cv.getId());
            }
            for(OdmaChoiceValue cv : piB.getChoices()) {
                if(!choices.contains(cv.getId())) {
                    return "Choice elements are different";
                }
                choices.remove(cv.getId());
            }
            if(!choices.isEmpty()) {
                return "Choice elements are different";
            }
        }
        if(referenceClassMatch && piA.getDataType() == OdmaType.REFERENCE.getNumericId()) {
        	OdmaClass refClassA = piA.getReferenceClass();
        	OdmaClass refClassB = piB.getReferenceClass();
        	if(refClassA == null) {
        		return "Property Info "+piA.getQName()+" has REFERENCE type but ReferenceClass is null";
        	}
        	if(refClassB == null) {
        		return "Property Info "+piB.getQName()+" has REFERENCE type but ReferenceClass is null";
        	}
            if(!piA.getReferenceClass().getQName().equals(piB.getReferenceClass().getQName())) {
                return "ReferenceClass is different: `"+piA.getReferenceClass().getQName()+"` and `"+piB.getReferenceClass().getQName()+"`";
            }
        }
        return null;
    }

    private static boolean isOrExtends(OdmaQName name, OdmaClass cls) {
        while(cls != null) {
            if(cls.getQName().equals(name)) {
                return true;
            }
            cls = cls.getSuperClass();
        }
        return false;
    }

    public static List<String> verifyOdmaObject(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaObject)) {
            result.add("Does not implement OdmaObject interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Class
        OdmaQName qnameOdmaClass = new OdmaQName("opendma","Class");
        try {
            OdmaProperty propOdmaClass = obj.getProperty(qnameOdmaClass);
            if(propOdmaClass.getName() == null) {
                result.add("Property opendma:Class qname is null");
            }
            if(!"opendma".equals(propOdmaClass.getName().getNamespace())) {
                result.add("Property opendma:Class qname namespace is not 'opendma', found instead'"+propOdmaClass.getName().getNamespace()+"'");
            }
            if(!"Class".equals(propOdmaClass.getName().getName())) {
                result.add("Property opendma:Class qname name is not 'Class', found instead'"+propOdmaClass.getName().getName()+"'");
            }
            if(propOdmaClass.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Class type is not 'REFERENCE'");
            }
            if(propOdmaClass.isMultiValue() != false) {
                result.add("Property opendma:Class MultiValue is not 'false'");
            }
            if(!propOdmaClass.isReadOnly()) {
                result.add("Property opendma:Class ReadOnly must be 'true'");
            }
            if(propOdmaClass.getValue() == null) {
                result.add("Property opendma:Class is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Class");
        }
        if(clazz != null && (new OdmaQName("opendma","Object")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredOdmaClass = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameOdmaClass.equals(pi.getQName())) {
                        if(piDeclaredOdmaClass == null) {
                            piDeclaredOdmaClass = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Class");
                        }
                    }
                }
            }
            if(piDeclaredOdmaClass == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Class");
            }
            if(piDeclaredOdmaClass != null) {
                if(!"opendma".equals(piDeclaredOdmaClass.getNamespace())) {
                    result.add("Property info for opendma:Class in declared properties qname namespace is not 'opendma'");
                }
                if(!"Class".equals(piDeclaredOdmaClass.getName())) {
                    result.add("Property info for opendma:Class in declared properties qname name is not 'Class'");
                }
                if(piDeclaredOdmaClass.getDataType() != 10) {
                    result.add("Property info for opendma:Class in declared properties data type is not '10'");
                }
                if(piDeclaredOdmaClass.isMultiValue() != false) {
                    result.add("Property info for opendma:Class in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredOdmaClass.isReadOnly() != true) {
                    result.add("Property info for opendma:Class in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Class")).equals(piDeclaredOdmaClass.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Class in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredOdmaClass.isHidden() != false) {
                    result.add("Property info for opendma:Class in declared properties Hidden is not 'false'");
                }
                if(piDeclaredOdmaClass.isRequired() != true) {
                result.add("Property info for opendma:Class in declared properties Required is not 'true'");
                }
                if(piDeclaredOdmaClass.isSystem() != true) {
                    result.add("Property info for opendma:Class in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllOdmaClass = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameOdmaClass.equals(pi.getQName())) {
                    if(piAllOdmaClass == null) {
                        piAllOdmaClass = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Class");
                    }
                }
            }
        }
        if(piAllOdmaClass == null) {
            result.add("All properties in class have no property info object with qname opendma:Class");
        }
        if(piAllOdmaClass != null) {
            if(!"opendma".equals(piAllOdmaClass.getNamespace())) {
                result.add("Property info for opendma:Class in all properties qname namespace is not 'opendma'");
            }
            if(!"Class".equals(piAllOdmaClass.getName())) {
                result.add("Property info for opendma:Class in all properties qname name is not 'Class'");
            }
            if(piAllOdmaClass.getDataType() != 10) {
                result.add("Property info for opendma:Class in all properties data type is not '10'");
            }
            if(piAllOdmaClass.isMultiValue() != false) {
                result.add("Property info for opendma:Class in all properties MultiValue is not 'false'");
            }
            if(piAllOdmaClass.isReadOnly() != true) {
                result.add("Property info for opendma:Class in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Class")).equals(piAllOdmaClass.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Class in all properties ReadOnly is not 'true'");
            }
            if(piAllOdmaClass.isHidden() != false) {
                result.add("Property info for opendma:Class in all properties Hidden is not 'false'");
            }
            if(piAllOdmaClass.isRequired() != true) {
                result.add("Property info for opendma:Class in all properties Required is not 'true'");
            }
            if(piAllOdmaClass.isSystem() != true) {
                result.add("Property info for opendma:Class in all properties System is not 'true'");
            }
        }
        // opendma:Id
        OdmaQName qnameId = new OdmaQName("opendma","Id");
        try {
            OdmaProperty propId = obj.getProperty(qnameId);
            if(propId.getName() == null) {
                result.add("Property opendma:Id qname is null");
            }
            if(!"opendma".equals(propId.getName().getNamespace())) {
                result.add("Property opendma:Id qname namespace is not 'opendma', found instead'"+propId.getName().getNamespace()+"'");
            }
            if(!"Id".equals(propId.getName().getName())) {
                result.add("Property opendma:Id qname name is not 'Id', found instead'"+propId.getName().getName()+"'");
            }
            if(propId.getType() != OdmaType.ID) {
                result.add("Property opendma:Id type is not 'ID'");
            }
            if(propId.isMultiValue() != false) {
                result.add("Property opendma:Id MultiValue is not 'false'");
            }
            if(!propId.isReadOnly()) {
                result.add("Property opendma:Id ReadOnly must be 'true'");
            }
            if(propId.getValue() == null) {
                result.add("Property opendma:Id is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Id");
        }
        if(clazz != null && (new OdmaQName("opendma","Object")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredId = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameId.equals(pi.getQName())) {
                        if(piDeclaredId == null) {
                            piDeclaredId = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Id");
                        }
                    }
                }
            }
            if(piDeclaredId == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Id");
            }
            if(piDeclaredId != null) {
                if(!"opendma".equals(piDeclaredId.getNamespace())) {
                    result.add("Property info for opendma:Id in declared properties qname namespace is not 'opendma'");
                }
                if(!"Id".equals(piDeclaredId.getName())) {
                    result.add("Property info for opendma:Id in declared properties qname name is not 'Id'");
                }
                if(piDeclaredId.getDataType() != 100) {
                    result.add("Property info for opendma:Id in declared properties data type is not '100'");
                }
                if(piDeclaredId.isMultiValue() != false) {
                    result.add("Property info for opendma:Id in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredId.isReadOnly() != true) {
                    result.add("Property info for opendma:Id in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredId.isHidden() != false) {
                    result.add("Property info for opendma:Id in declared properties Hidden is not 'false'");
                }
                if(piDeclaredId.isRequired() != true) {
                result.add("Property info for opendma:Id in declared properties Required is not 'true'");
                }
                if(piDeclaredId.isSystem() != true) {
                    result.add("Property info for opendma:Id in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllId = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameId.equals(pi.getQName())) {
                    if(piAllId == null) {
                        piAllId = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Id");
                    }
                }
            }
        }
        if(piAllId == null) {
            result.add("All properties in class have no property info object with qname opendma:Id");
        }
        if(piAllId != null) {
            if(!"opendma".equals(piAllId.getNamespace())) {
                result.add("Property info for opendma:Id in all properties qname namespace is not 'opendma'");
            }
            if(!"Id".equals(piAllId.getName())) {
                result.add("Property info for opendma:Id in all properties qname name is not 'Id'");
            }
            if(piAllId.getDataType() != 100) {
                result.add("Property info for opendma:Id in all properties data type is not '100'");
            }
            if(piAllId.isMultiValue() != false) {
                result.add("Property info for opendma:Id in all properties MultiValue is not 'false'");
            }
            if(piAllId.isReadOnly() != true) {
                result.add("Property info for opendma:Id in all properties ReadOnly is not 'true'");
            }
            if(piAllId.isHidden() != false) {
                result.add("Property info for opendma:Id in all properties Hidden is not 'false'");
            }
            if(piAllId.isRequired() != true) {
                result.add("Property info for opendma:Id in all properties Required is not 'true'");
            }
            if(piAllId.isSystem() != true) {
                result.add("Property info for opendma:Id in all properties System is not 'true'");
            }
        }
        // opendma:Guid
        OdmaQName qnameGuid = new OdmaQName("opendma","Guid");
        try {
            OdmaProperty propGuid = obj.getProperty(qnameGuid);
            if(propGuid.getName() == null) {
                result.add("Property opendma:Guid qname is null");
            }
            if(!"opendma".equals(propGuid.getName().getNamespace())) {
                result.add("Property opendma:Guid qname namespace is not 'opendma', found instead'"+propGuid.getName().getNamespace()+"'");
            }
            if(!"Guid".equals(propGuid.getName().getName())) {
                result.add("Property opendma:Guid qname name is not 'Guid', found instead'"+propGuid.getName().getName()+"'");
            }
            if(propGuid.getType() != OdmaType.GUID) {
                result.add("Property opendma:Guid type is not 'GUID'");
            }
            if(propGuid.isMultiValue() != false) {
                result.add("Property opendma:Guid MultiValue is not 'false'");
            }
            if(!propGuid.isReadOnly()) {
                result.add("Property opendma:Guid ReadOnly must be 'true'");
            }
            if(propGuid.getValue() == null) {
                result.add("Property opendma:Guid is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Guid");
        }
        if(clazz != null && (new OdmaQName("opendma","Object")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredGuid = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameGuid.equals(pi.getQName())) {
                        if(piDeclaredGuid == null) {
                            piDeclaredGuid = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Guid");
                        }
                    }
                }
            }
            if(piDeclaredGuid == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Guid");
            }
            if(piDeclaredGuid != null) {
                if(!"opendma".equals(piDeclaredGuid.getNamespace())) {
                    result.add("Property info for opendma:Guid in declared properties qname namespace is not 'opendma'");
                }
                if(!"Guid".equals(piDeclaredGuid.getName())) {
                    result.add("Property info for opendma:Guid in declared properties qname name is not 'Guid'");
                }
                if(piDeclaredGuid.getDataType() != 101) {
                    result.add("Property info for opendma:Guid in declared properties data type is not '101'");
                }
                if(piDeclaredGuid.isMultiValue() != false) {
                    result.add("Property info for opendma:Guid in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredGuid.isReadOnly() != true) {
                    result.add("Property info for opendma:Guid in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredGuid.isHidden() != false) {
                    result.add("Property info for opendma:Guid in declared properties Hidden is not 'false'");
                }
                if(piDeclaredGuid.isRequired() != true) {
                result.add("Property info for opendma:Guid in declared properties Required is not 'true'");
                }
                if(piDeclaredGuid.isSystem() != true) {
                    result.add("Property info for opendma:Guid in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllGuid = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameGuid.equals(pi.getQName())) {
                    if(piAllGuid == null) {
                        piAllGuid = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Guid");
                    }
                }
            }
        }
        if(piAllGuid == null) {
            result.add("All properties in class have no property info object with qname opendma:Guid");
        }
        if(piAllGuid != null) {
            if(!"opendma".equals(piAllGuid.getNamespace())) {
                result.add("Property info for opendma:Guid in all properties qname namespace is not 'opendma'");
            }
            if(!"Guid".equals(piAllGuid.getName())) {
                result.add("Property info for opendma:Guid in all properties qname name is not 'Guid'");
            }
            if(piAllGuid.getDataType() != 101) {
                result.add("Property info for opendma:Guid in all properties data type is not '101'");
            }
            if(piAllGuid.isMultiValue() != false) {
                result.add("Property info for opendma:Guid in all properties MultiValue is not 'false'");
            }
            if(piAllGuid.isReadOnly() != true) {
                result.add("Property info for opendma:Guid in all properties ReadOnly is not 'true'");
            }
            if(piAllGuid.isHidden() != false) {
                result.add("Property info for opendma:Guid in all properties Hidden is not 'false'");
            }
            if(piAllGuid.isRequired() != true) {
                result.add("Property info for opendma:Guid in all properties Required is not 'true'");
            }
            if(piAllGuid.isSystem() != true) {
                result.add("Property info for opendma:Guid in all properties System is not 'true'");
            }
        }
        // opendma:Repository
        OdmaQName qnameRepository = new OdmaQName("opendma","Repository");
        try {
            OdmaProperty propRepository = obj.getProperty(qnameRepository);
            if(propRepository.getName() == null) {
                result.add("Property opendma:Repository qname is null");
            }
            if(!"opendma".equals(propRepository.getName().getNamespace())) {
                result.add("Property opendma:Repository qname namespace is not 'opendma', found instead'"+propRepository.getName().getNamespace()+"'");
            }
            if(!"Repository".equals(propRepository.getName().getName())) {
                result.add("Property opendma:Repository qname name is not 'Repository', found instead'"+propRepository.getName().getName()+"'");
            }
            if(propRepository.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Repository type is not 'REFERENCE'");
            }
            if(propRepository.isMultiValue() != false) {
                result.add("Property opendma:Repository MultiValue is not 'false'");
            }
            if(!propRepository.isReadOnly()) {
                result.add("Property opendma:Repository ReadOnly must be 'true'");
            }
            if(propRepository.getValue() == null) {
                result.add("Property opendma:Repository is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Repository");
        }
        if(clazz != null && (new OdmaQName("opendma","Object")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredRepository = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameRepository.equals(pi.getQName())) {
                        if(piDeclaredRepository == null) {
                            piDeclaredRepository = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Repository");
                        }
                    }
                }
            }
            if(piDeclaredRepository == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Repository");
            }
            if(piDeclaredRepository != null) {
                if(!"opendma".equals(piDeclaredRepository.getNamespace())) {
                    result.add("Property info for opendma:Repository in declared properties qname namespace is not 'opendma'");
                }
                if(!"Repository".equals(piDeclaredRepository.getName())) {
                    result.add("Property info for opendma:Repository in declared properties qname name is not 'Repository'");
                }
                if(piDeclaredRepository.getDataType() != 10) {
                    result.add("Property info for opendma:Repository in declared properties data type is not '10'");
                }
                if(piDeclaredRepository.isMultiValue() != false) {
                    result.add("Property info for opendma:Repository in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredRepository.isReadOnly() != true) {
                    result.add("Property info for opendma:Repository in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Repository")).equals(piDeclaredRepository.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Repository in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredRepository.isHidden() != false) {
                    result.add("Property info for opendma:Repository in declared properties Hidden is not 'false'");
                }
                if(piDeclaredRepository.isRequired() != true) {
                result.add("Property info for opendma:Repository in declared properties Required is not 'true'");
                }
                if(piDeclaredRepository.isSystem() != true) {
                    result.add("Property info for opendma:Repository in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllRepository = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameRepository.equals(pi.getQName())) {
                    if(piAllRepository == null) {
                        piAllRepository = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Repository");
                    }
                }
            }
        }
        if(piAllRepository == null) {
            result.add("All properties in class have no property info object with qname opendma:Repository");
        }
        if(piAllRepository != null) {
            if(!"opendma".equals(piAllRepository.getNamespace())) {
                result.add("Property info for opendma:Repository in all properties qname namespace is not 'opendma'");
            }
            if(!"Repository".equals(piAllRepository.getName())) {
                result.add("Property info for opendma:Repository in all properties qname name is not 'Repository'");
            }
            if(piAllRepository.getDataType() != 10) {
                result.add("Property info for opendma:Repository in all properties data type is not '10'");
            }
            if(piAllRepository.isMultiValue() != false) {
                result.add("Property info for opendma:Repository in all properties MultiValue is not 'false'");
            }
            if(piAllRepository.isReadOnly() != true) {
                result.add("Property info for opendma:Repository in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Repository")).equals(piAllRepository.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Repository in all properties ReadOnly is not 'true'");
            }
            if(piAllRepository.isHidden() != false) {
                result.add("Property info for opendma:Repository in all properties Hidden is not 'false'");
            }
            if(piAllRepository.isRequired() != true) {
                result.add("Property info for opendma:Repository in all properties Required is not 'true'");
            }
            if(piAllRepository.isSystem() != true) {
                result.add("Property info for opendma:Repository in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaClass(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaClass)) {
            result.add("Does not implement OdmaClass interface");
        }
        if(obj instanceof OdmaClass) {
            result.addAll(verifyClassBaseline((OdmaClass)obj));
        } else {
            result.add("Skipping class baseline verification");
            result.addAll(verifyObjectBaseline(obj));
        }
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Name
        OdmaQName qnameName = new OdmaQName("opendma","Name");
        try {
            OdmaProperty propName = obj.getProperty(qnameName);
            if(propName.getName() == null) {
                result.add("Property opendma:Name qname is null");
            }
            if(!"opendma".equals(propName.getName().getNamespace())) {
                result.add("Property opendma:Name qname namespace is not 'opendma', found instead'"+propName.getName().getNamespace()+"'");
            }
            if(!"Name".equals(propName.getName().getName())) {
                result.add("Property opendma:Name qname name is not 'Name', found instead'"+propName.getName().getName()+"'");
            }
            if(propName.getType() != OdmaType.STRING) {
                result.add("Property opendma:Name type is not 'STRING'");
            }
            if(propName.isMultiValue() != false) {
                result.add("Property opendma:Name MultiValue is not 'false'");
            }
            if(propName.getValue() == null) {
                result.add("Property opendma:Name is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Name");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameName.equals(pi.getQName())) {
                        if(piDeclaredName == null) {
                            piDeclaredName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Name");
                        }
                    }
                }
            }
            if(piDeclaredName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Name");
            }
            if(piDeclaredName != null) {
                if(!"opendma".equals(piDeclaredName.getNamespace())) {
                    result.add("Property info for opendma:Name in declared properties qname namespace is not 'opendma'");
                }
                if(!"Name".equals(piDeclaredName.getName())) {
                    result.add("Property info for opendma:Name in declared properties qname name is not 'Name'");
                }
                if(piDeclaredName.getDataType() != 1) {
                    result.add("Property info for opendma:Name in declared properties data type is not '1'");
                }
                if(piDeclaredName.isMultiValue() != false) {
                    result.add("Property info for opendma:Name in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredName.isReadOnly() != false) {
                    result.add("Property info for opendma:Name in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredName.isHidden() != false) {
                    result.add("Property info for opendma:Name in declared properties Hidden is not 'false'");
                }
                if(piDeclaredName.isRequired() != true) {
                result.add("Property info for opendma:Name in declared properties Required is not 'true'");
                }
                if(piDeclaredName.isSystem() != true) {
                    result.add("Property info for opendma:Name in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameName.equals(pi.getQName())) {
                    if(piAllName == null) {
                        piAllName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Name");
                    }
                }
            }
        }
        if(piAllName == null) {
            result.add("All properties in class have no property info object with qname opendma:Name");
        }
        if(piAllName != null) {
            if(!"opendma".equals(piAllName.getNamespace())) {
                result.add("Property info for opendma:Name in all properties qname namespace is not 'opendma'");
            }
            if(!"Name".equals(piAllName.getName())) {
                result.add("Property info for opendma:Name in all properties qname name is not 'Name'");
            }
            if(piAllName.getDataType() != 1) {
                result.add("Property info for opendma:Name in all properties data type is not '1'");
            }
            if(piAllName.isMultiValue() != false) {
                result.add("Property info for opendma:Name in all properties MultiValue is not 'false'");
            }
            if(piAllName.isReadOnly() != false) {
                result.add("Property info for opendma:Name in all properties ReadOnly is not 'false'");
            }
            if(piAllName.isHidden() != false) {
                result.add("Property info for opendma:Name in all properties Hidden is not 'false'");
            }
            if(piAllName.isRequired() != true) {
                result.add("Property info for opendma:Name in all properties Required is not 'true'");
            }
            if(piAllName.isSystem() != true) {
                result.add("Property info for opendma:Name in all properties System is not 'true'");
            }
        }
        // opendma:Namespace
        OdmaQName qnameNamespace = new OdmaQName("opendma","Namespace");
        try {
            OdmaProperty propNamespace = obj.getProperty(qnameNamespace);
            if(propNamespace.getName() == null) {
                result.add("Property opendma:Namespace qname is null");
            }
            if(!"opendma".equals(propNamespace.getName().getNamespace())) {
                result.add("Property opendma:Namespace qname namespace is not 'opendma', found instead'"+propNamespace.getName().getNamespace()+"'");
            }
            if(!"Namespace".equals(propNamespace.getName().getName())) {
                result.add("Property opendma:Namespace qname name is not 'Namespace', found instead'"+propNamespace.getName().getName()+"'");
            }
            if(propNamespace.getType() != OdmaType.STRING) {
                result.add("Property opendma:Namespace type is not 'STRING'");
            }
            if(propNamespace.isMultiValue() != false) {
                result.add("Property opendma:Namespace MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Namespace");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredNamespace = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameNamespace.equals(pi.getQName())) {
                        if(piDeclaredNamespace == null) {
                            piDeclaredNamespace = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Namespace");
                        }
                    }
                }
            }
            if(piDeclaredNamespace == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Namespace");
            }
            if(piDeclaredNamespace != null) {
                if(!"opendma".equals(piDeclaredNamespace.getNamespace())) {
                    result.add("Property info for opendma:Namespace in declared properties qname namespace is not 'opendma'");
                }
                if(!"Namespace".equals(piDeclaredNamespace.getName())) {
                    result.add("Property info for opendma:Namespace in declared properties qname name is not 'Namespace'");
                }
                if(piDeclaredNamespace.getDataType() != 1) {
                    result.add("Property info for opendma:Namespace in declared properties data type is not '1'");
                }
                if(piDeclaredNamespace.isMultiValue() != false) {
                    result.add("Property info for opendma:Namespace in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredNamespace.isReadOnly() != false) {
                    result.add("Property info for opendma:Namespace in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredNamespace.isHidden() != false) {
                    result.add("Property info for opendma:Namespace in declared properties Hidden is not 'false'");
                }
                if(piDeclaredNamespace.isRequired() != false) {
                result.add("Property info for opendma:Namespace in declared properties Required is not 'false'");
                }
                if(piDeclaredNamespace.isSystem() != true) {
                    result.add("Property info for opendma:Namespace in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllNamespace = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameNamespace.equals(pi.getQName())) {
                    if(piAllNamespace == null) {
                        piAllNamespace = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Namespace");
                    }
                }
            }
        }
        if(piAllNamespace == null) {
            result.add("All properties in class have no property info object with qname opendma:Namespace");
        }
        if(piAllNamespace != null) {
            if(!"opendma".equals(piAllNamespace.getNamespace())) {
                result.add("Property info for opendma:Namespace in all properties qname namespace is not 'opendma'");
            }
            if(!"Namespace".equals(piAllNamespace.getName())) {
                result.add("Property info for opendma:Namespace in all properties qname name is not 'Namespace'");
            }
            if(piAllNamespace.getDataType() != 1) {
                result.add("Property info for opendma:Namespace in all properties data type is not '1'");
            }
            if(piAllNamespace.isMultiValue() != false) {
                result.add("Property info for opendma:Namespace in all properties MultiValue is not 'false'");
            }
            if(piAllNamespace.isReadOnly() != false) {
                result.add("Property info for opendma:Namespace in all properties ReadOnly is not 'false'");
            }
            if(piAllNamespace.isHidden() != false) {
                result.add("Property info for opendma:Namespace in all properties Hidden is not 'false'");
            }
            if(piAllNamespace.isRequired() != false) {
                result.add("Property info for opendma:Namespace in all properties Required is not 'false'");
            }
            if(piAllNamespace.isSystem() != true) {
                result.add("Property info for opendma:Namespace in all properties System is not 'true'");
            }
        }
        // opendma:DisplayName
        OdmaQName qnameDisplayName = new OdmaQName("opendma","DisplayName");
        try {
            OdmaProperty propDisplayName = obj.getProperty(qnameDisplayName);
            if(propDisplayName.getName() == null) {
                result.add("Property opendma:DisplayName qname is null");
            }
            if(!"opendma".equals(propDisplayName.getName().getNamespace())) {
                result.add("Property opendma:DisplayName qname namespace is not 'opendma', found instead'"+propDisplayName.getName().getNamespace()+"'");
            }
            if(!"DisplayName".equals(propDisplayName.getName().getName())) {
                result.add("Property opendma:DisplayName qname name is not 'DisplayName', found instead'"+propDisplayName.getName().getName()+"'");
            }
            if(propDisplayName.getType() != OdmaType.STRING) {
                result.add("Property opendma:DisplayName type is not 'STRING'");
            }
            if(propDisplayName.isMultiValue() != false) {
                result.add("Property opendma:DisplayName MultiValue is not 'false'");
            }
            if(propDisplayName.getValue() == null) {
                result.add("Property opendma:DisplayName is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DisplayName");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDisplayName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDisplayName.equals(pi.getQName())) {
                        if(piDeclaredDisplayName == null) {
                            piDeclaredDisplayName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DisplayName");
                        }
                    }
                }
            }
            if(piDeclaredDisplayName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DisplayName");
            }
            if(piDeclaredDisplayName != null) {
                if(!"opendma".equals(piDeclaredDisplayName.getNamespace())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname namespace is not 'opendma'");
                }
                if(!"DisplayName".equals(piDeclaredDisplayName.getName())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname name is not 'DisplayName'");
                }
                if(piDeclaredDisplayName.getDataType() != 1) {
                    result.add("Property info for opendma:DisplayName in declared properties data type is not '1'");
                }
                if(piDeclaredDisplayName.isMultiValue() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredDisplayName.isReadOnly() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDisplayName.isHidden() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in declared properties Required is not 'true'");
                }
                if(piDeclaredDisplayName.isSystem() != true) {
                    result.add("Property info for opendma:DisplayName in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDisplayName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDisplayName.equals(pi.getQName())) {
                    if(piAllDisplayName == null) {
                        piAllDisplayName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DisplayName");
                    }
                }
            }
        }
        if(piAllDisplayName == null) {
            result.add("All properties in class have no property info object with qname opendma:DisplayName");
        }
        if(piAllDisplayName != null) {
            if(!"opendma".equals(piAllDisplayName.getNamespace())) {
                result.add("Property info for opendma:DisplayName in all properties qname namespace is not 'opendma'");
            }
            if(!"DisplayName".equals(piAllDisplayName.getName())) {
                result.add("Property info for opendma:DisplayName in all properties qname name is not 'DisplayName'");
            }
            if(piAllDisplayName.getDataType() != 1) {
                result.add("Property info for opendma:DisplayName in all properties data type is not '1'");
            }
            if(piAllDisplayName.isMultiValue() != false) {
                result.add("Property info for opendma:DisplayName in all properties MultiValue is not 'false'");
            }
            if(piAllDisplayName.isReadOnly() != false) {
                result.add("Property info for opendma:DisplayName in all properties ReadOnly is not 'false'");
            }
            if(piAllDisplayName.isHidden() != false) {
                result.add("Property info for opendma:DisplayName in all properties Hidden is not 'false'");
            }
            if(piAllDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in all properties Required is not 'true'");
            }
            if(piAllDisplayName.isSystem() != true) {
                result.add("Property info for opendma:DisplayName in all properties System is not 'true'");
            }
        }
        // opendma:SuperClass
        OdmaQName qnameSuperClass = new OdmaQName("opendma","SuperClass");
        try {
            OdmaProperty propSuperClass = obj.getProperty(qnameSuperClass);
            if(propSuperClass.getName() == null) {
                result.add("Property opendma:SuperClass qname is null");
            }
            if(!"opendma".equals(propSuperClass.getName().getNamespace())) {
                result.add("Property opendma:SuperClass qname namespace is not 'opendma', found instead'"+propSuperClass.getName().getNamespace()+"'");
            }
            if(!"SuperClass".equals(propSuperClass.getName().getName())) {
                result.add("Property opendma:SuperClass qname name is not 'SuperClass', found instead'"+propSuperClass.getName().getName()+"'");
            }
            if(propSuperClass.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:SuperClass type is not 'REFERENCE'");
            }
            if(propSuperClass.isMultiValue() != false) {
                result.add("Property opendma:SuperClass MultiValue is not 'false'");
            }
            if(!propSuperClass.isReadOnly()) {
                result.add("Property opendma:SuperClass ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:SuperClass");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredSuperClass = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameSuperClass.equals(pi.getQName())) {
                        if(piDeclaredSuperClass == null) {
                            piDeclaredSuperClass = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:SuperClass");
                        }
                    }
                }
            }
            if(piDeclaredSuperClass == null) {
                result.add("Declared properties in class have no property info object with qname opendma:SuperClass");
            }
            if(piDeclaredSuperClass != null) {
                if(!"opendma".equals(piDeclaredSuperClass.getNamespace())) {
                    result.add("Property info for opendma:SuperClass in declared properties qname namespace is not 'opendma'");
                }
                if(!"SuperClass".equals(piDeclaredSuperClass.getName())) {
                    result.add("Property info for opendma:SuperClass in declared properties qname name is not 'SuperClass'");
                }
                if(piDeclaredSuperClass.getDataType() != 10) {
                    result.add("Property info for opendma:SuperClass in declared properties data type is not '10'");
                }
                if(piDeclaredSuperClass.isMultiValue() != false) {
                    result.add("Property info for opendma:SuperClass in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredSuperClass.isReadOnly() != true) {
                    result.add("Property info for opendma:SuperClass in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Class")).equals(piDeclaredSuperClass.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:SuperClass in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredSuperClass.isHidden() != false) {
                    result.add("Property info for opendma:SuperClass in declared properties Hidden is not 'false'");
                }
                if(piDeclaredSuperClass.isRequired() != false) {
                result.add("Property info for opendma:SuperClass in declared properties Required is not 'false'");
                }
                if(piDeclaredSuperClass.isSystem() != true) {
                    result.add("Property info for opendma:SuperClass in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllSuperClass = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameSuperClass.equals(pi.getQName())) {
                    if(piAllSuperClass == null) {
                        piAllSuperClass = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:SuperClass");
                    }
                }
            }
        }
        if(piAllSuperClass == null) {
            result.add("All properties in class have no property info object with qname opendma:SuperClass");
        }
        if(piAllSuperClass != null) {
            if(!"opendma".equals(piAllSuperClass.getNamespace())) {
                result.add("Property info for opendma:SuperClass in all properties qname namespace is not 'opendma'");
            }
            if(!"SuperClass".equals(piAllSuperClass.getName())) {
                result.add("Property info for opendma:SuperClass in all properties qname name is not 'SuperClass'");
            }
            if(piAllSuperClass.getDataType() != 10) {
                result.add("Property info for opendma:SuperClass in all properties data type is not '10'");
            }
            if(piAllSuperClass.isMultiValue() != false) {
                result.add("Property info for opendma:SuperClass in all properties MultiValue is not 'false'");
            }
            if(piAllSuperClass.isReadOnly() != true) {
                result.add("Property info for opendma:SuperClass in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Class")).equals(piAllSuperClass.getReferenceClass().getQName())) {
                result.add("Property info for opendma:SuperClass in all properties ReadOnly is not 'true'");
            }
            if(piAllSuperClass.isHidden() != false) {
                result.add("Property info for opendma:SuperClass in all properties Hidden is not 'false'");
            }
            if(piAllSuperClass.isRequired() != false) {
                result.add("Property info for opendma:SuperClass in all properties Required is not 'false'");
            }
            if(piAllSuperClass.isSystem() != true) {
                result.add("Property info for opendma:SuperClass in all properties System is not 'true'");
            }
        }
        // opendma:Aspects
        OdmaQName qnameAspects = new OdmaQName("opendma","Aspects");
        try {
            OdmaProperty propAspects = obj.getProperty(qnameAspects);
            if(propAspects.getName() == null) {
                result.add("Property opendma:Aspects qname is null");
            }
            if(!"opendma".equals(propAspects.getName().getNamespace())) {
                result.add("Property opendma:Aspects qname namespace is not 'opendma', found instead'"+propAspects.getName().getNamespace()+"'");
            }
            if(!"Aspects".equals(propAspects.getName().getName())) {
                result.add("Property opendma:Aspects qname name is not 'Aspects', found instead'"+propAspects.getName().getName()+"'");
            }
            if(propAspects.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Aspects type is not 'REFERENCE'");
            }
            if(propAspects.isMultiValue() != true) {
                result.add("Property opendma:Aspects MultiValue is not 'true'");
            }
            if(propAspects.getValue() == null) {
                result.add("Property opendma:Aspects is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Aspects");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredAspects = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameAspects.equals(pi.getQName())) {
                        if(piDeclaredAspects == null) {
                            piDeclaredAspects = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Aspects");
                        }
                    }
                }
            }
            if(piDeclaredAspects == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Aspects");
            }
            if(piDeclaredAspects != null) {
                if(!"opendma".equals(piDeclaredAspects.getNamespace())) {
                    result.add("Property info for opendma:Aspects in declared properties qname namespace is not 'opendma'");
                }
                if(!"Aspects".equals(piDeclaredAspects.getName())) {
                    result.add("Property info for opendma:Aspects in declared properties qname name is not 'Aspects'");
                }
                if(piDeclaredAspects.getDataType() != 10) {
                    result.add("Property info for opendma:Aspects in declared properties data type is not '10'");
                }
                if(piDeclaredAspects.isMultiValue() != true) {
                    result.add("Property info for opendma:Aspects in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredAspects.isReadOnly() != false) {
                    result.add("Property info for opendma:Aspects in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","Class")).equals(piDeclaredAspects.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Aspects in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredAspects.isHidden() != false) {
                    result.add("Property info for opendma:Aspects in declared properties Hidden is not 'false'");
                }
                if(piDeclaredAspects.isRequired() != false) {
                result.add("Property info for opendma:Aspects in declared properties Required is not 'false'");
                }
                if(piDeclaredAspects.isSystem() != true) {
                    result.add("Property info for opendma:Aspects in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllAspects = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameAspects.equals(pi.getQName())) {
                    if(piAllAspects == null) {
                        piAllAspects = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Aspects");
                    }
                }
            }
        }
        if(piAllAspects == null) {
            result.add("All properties in class have no property info object with qname opendma:Aspects");
        }
        if(piAllAspects != null) {
            if(!"opendma".equals(piAllAspects.getNamespace())) {
                result.add("Property info for opendma:Aspects in all properties qname namespace is not 'opendma'");
            }
            if(!"Aspects".equals(piAllAspects.getName())) {
                result.add("Property info for opendma:Aspects in all properties qname name is not 'Aspects'");
            }
            if(piAllAspects.getDataType() != 10) {
                result.add("Property info for opendma:Aspects in all properties data type is not '10'");
            }
            if(piAllAspects.isMultiValue() != true) {
                result.add("Property info for opendma:Aspects in all properties MultiValue is not 'true'");
            }
            if(piAllAspects.isReadOnly() != false) {
                result.add("Property info for opendma:Aspects in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","Class")).equals(piAllAspects.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Aspects in all properties ReadOnly is not 'false'");
            }
            if(piAllAspects.isHidden() != false) {
                result.add("Property info for opendma:Aspects in all properties Hidden is not 'false'");
            }
            if(piAllAspects.isRequired() != false) {
                result.add("Property info for opendma:Aspects in all properties Required is not 'false'");
            }
            if(piAllAspects.isSystem() != true) {
                result.add("Property info for opendma:Aspects in all properties System is not 'true'");
            }
        }
        // opendma:DeclaredProperties
        OdmaQName qnameDeclaredProperties = new OdmaQName("opendma","DeclaredProperties");
        try {
            OdmaProperty propDeclaredProperties = obj.getProperty(qnameDeclaredProperties);
            if(propDeclaredProperties.getName() == null) {
                result.add("Property opendma:DeclaredProperties qname is null");
            }
            if(!"opendma".equals(propDeclaredProperties.getName().getNamespace())) {
                result.add("Property opendma:DeclaredProperties qname namespace is not 'opendma', found instead'"+propDeclaredProperties.getName().getNamespace()+"'");
            }
            if(!"DeclaredProperties".equals(propDeclaredProperties.getName().getName())) {
                result.add("Property opendma:DeclaredProperties qname name is not 'DeclaredProperties', found instead'"+propDeclaredProperties.getName().getName()+"'");
            }
            if(propDeclaredProperties.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:DeclaredProperties type is not 'REFERENCE'");
            }
            if(propDeclaredProperties.isMultiValue() != true) {
                result.add("Property opendma:DeclaredProperties MultiValue is not 'true'");
            }
            if(propDeclaredProperties.getValue() == null) {
                result.add("Property opendma:DeclaredProperties is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DeclaredProperties");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDeclaredProperties = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDeclaredProperties.equals(pi.getQName())) {
                        if(piDeclaredDeclaredProperties == null) {
                            piDeclaredDeclaredProperties = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DeclaredProperties");
                        }
                    }
                }
            }
            if(piDeclaredDeclaredProperties == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DeclaredProperties");
            }
            if(piDeclaredDeclaredProperties != null) {
                if(!"opendma".equals(piDeclaredDeclaredProperties.getNamespace())) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties qname namespace is not 'opendma'");
                }
                if(!"DeclaredProperties".equals(piDeclaredDeclaredProperties.getName())) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties qname name is not 'DeclaredProperties'");
                }
                if(piDeclaredDeclaredProperties.getDataType() != 10) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties data type is not '10'");
                }
                if(piDeclaredDeclaredProperties.isMultiValue() != true) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredDeclaredProperties.isReadOnly() != false) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","PropertyInfo")).equals(piDeclaredDeclaredProperties.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDeclaredProperties.isHidden() != false) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDeclaredProperties.isRequired() != false) {
                result.add("Property info for opendma:DeclaredProperties in declared properties Required is not 'false'");
                }
                if(piDeclaredDeclaredProperties.isSystem() != true) {
                    result.add("Property info for opendma:DeclaredProperties in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDeclaredProperties = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDeclaredProperties.equals(pi.getQName())) {
                    if(piAllDeclaredProperties == null) {
                        piAllDeclaredProperties = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DeclaredProperties");
                    }
                }
            }
        }
        if(piAllDeclaredProperties == null) {
            result.add("All properties in class have no property info object with qname opendma:DeclaredProperties");
        }
        if(piAllDeclaredProperties != null) {
            if(!"opendma".equals(piAllDeclaredProperties.getNamespace())) {
                result.add("Property info for opendma:DeclaredProperties in all properties qname namespace is not 'opendma'");
            }
            if(!"DeclaredProperties".equals(piAllDeclaredProperties.getName())) {
                result.add("Property info for opendma:DeclaredProperties in all properties qname name is not 'DeclaredProperties'");
            }
            if(piAllDeclaredProperties.getDataType() != 10) {
                result.add("Property info for opendma:DeclaredProperties in all properties data type is not '10'");
            }
            if(piAllDeclaredProperties.isMultiValue() != true) {
                result.add("Property info for opendma:DeclaredProperties in all properties MultiValue is not 'true'");
            }
            if(piAllDeclaredProperties.isReadOnly() != false) {
                result.add("Property info for opendma:DeclaredProperties in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","PropertyInfo")).equals(piAllDeclaredProperties.getReferenceClass().getQName())) {
                result.add("Property info for opendma:DeclaredProperties in all properties ReadOnly is not 'false'");
            }
            if(piAllDeclaredProperties.isHidden() != false) {
                result.add("Property info for opendma:DeclaredProperties in all properties Hidden is not 'false'");
            }
            if(piAllDeclaredProperties.isRequired() != false) {
                result.add("Property info for opendma:DeclaredProperties in all properties Required is not 'false'");
            }
            if(piAllDeclaredProperties.isSystem() != true) {
                result.add("Property info for opendma:DeclaredProperties in all properties System is not 'true'");
            }
        }
        // opendma:Properties
        OdmaQName qnameProperties = new OdmaQName("opendma","Properties");
        try {
            OdmaProperty propProperties = obj.getProperty(qnameProperties);
            if(propProperties.getName() == null) {
                result.add("Property opendma:Properties qname is null");
            }
            if(!"opendma".equals(propProperties.getName().getNamespace())) {
                result.add("Property opendma:Properties qname namespace is not 'opendma', found instead'"+propProperties.getName().getNamespace()+"'");
            }
            if(!"Properties".equals(propProperties.getName().getName())) {
                result.add("Property opendma:Properties qname name is not 'Properties', found instead'"+propProperties.getName().getName()+"'");
            }
            if(propProperties.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Properties type is not 'REFERENCE'");
            }
            if(propProperties.isMultiValue() != true) {
                result.add("Property opendma:Properties MultiValue is not 'true'");
            }
            if(!propProperties.isReadOnly()) {
                result.add("Property opendma:Properties ReadOnly must be 'true'");
            }
            if(propProperties.getValue() == null) {
                result.add("Property opendma:Properties is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Properties");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredProperties = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameProperties.equals(pi.getQName())) {
                        if(piDeclaredProperties == null) {
                            piDeclaredProperties = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Properties");
                        }
                    }
                }
            }
            if(piDeclaredProperties == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Properties");
            }
            if(piDeclaredProperties != null) {
                if(!"opendma".equals(piDeclaredProperties.getNamespace())) {
                    result.add("Property info for opendma:Properties in declared properties qname namespace is not 'opendma'");
                }
                if(!"Properties".equals(piDeclaredProperties.getName())) {
                    result.add("Property info for opendma:Properties in declared properties qname name is not 'Properties'");
                }
                if(piDeclaredProperties.getDataType() != 10) {
                    result.add("Property info for opendma:Properties in declared properties data type is not '10'");
                }
                if(piDeclaredProperties.isMultiValue() != true) {
                    result.add("Property info for opendma:Properties in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredProperties.isReadOnly() != true) {
                    result.add("Property info for opendma:Properties in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","PropertyInfo")).equals(piDeclaredProperties.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Properties in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredProperties.isHidden() != false) {
                    result.add("Property info for opendma:Properties in declared properties Hidden is not 'false'");
                }
                if(piDeclaredProperties.isRequired() != false) {
                result.add("Property info for opendma:Properties in declared properties Required is not 'false'");
                }
                if(piDeclaredProperties.isSystem() != true) {
                    result.add("Property info for opendma:Properties in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllProperties = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameProperties.equals(pi.getQName())) {
                    if(piAllProperties == null) {
                        piAllProperties = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Properties");
                    }
                }
            }
        }
        if(piAllProperties == null) {
            result.add("All properties in class have no property info object with qname opendma:Properties");
        }
        if(piAllProperties != null) {
            if(!"opendma".equals(piAllProperties.getNamespace())) {
                result.add("Property info for opendma:Properties in all properties qname namespace is not 'opendma'");
            }
            if(!"Properties".equals(piAllProperties.getName())) {
                result.add("Property info for opendma:Properties in all properties qname name is not 'Properties'");
            }
            if(piAllProperties.getDataType() != 10) {
                result.add("Property info for opendma:Properties in all properties data type is not '10'");
            }
            if(piAllProperties.isMultiValue() != true) {
                result.add("Property info for opendma:Properties in all properties MultiValue is not 'true'");
            }
            if(piAllProperties.isReadOnly() != true) {
                result.add("Property info for opendma:Properties in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","PropertyInfo")).equals(piAllProperties.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Properties in all properties ReadOnly is not 'true'");
            }
            if(piAllProperties.isHidden() != false) {
                result.add("Property info for opendma:Properties in all properties Hidden is not 'false'");
            }
            if(piAllProperties.isRequired() != false) {
                result.add("Property info for opendma:Properties in all properties Required is not 'false'");
            }
            if(piAllProperties.isSystem() != true) {
                result.add("Property info for opendma:Properties in all properties System is not 'true'");
            }
        }
        // opendma:Aspect
        OdmaQName qnameAspect = new OdmaQName("opendma","Aspect");
        try {
            OdmaProperty propAspect = obj.getProperty(qnameAspect);
            if(propAspect.getName() == null) {
                result.add("Property opendma:Aspect qname is null");
            }
            if(!"opendma".equals(propAspect.getName().getNamespace())) {
                result.add("Property opendma:Aspect qname namespace is not 'opendma', found instead'"+propAspect.getName().getNamespace()+"'");
            }
            if(!"Aspect".equals(propAspect.getName().getName())) {
                result.add("Property opendma:Aspect qname name is not 'Aspect', found instead'"+propAspect.getName().getName()+"'");
            }
            if(propAspect.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:Aspect type is not 'BOOLEAN'");
            }
            if(propAspect.isMultiValue() != false) {
                result.add("Property opendma:Aspect MultiValue is not 'false'");
            }
            if(!propAspect.isReadOnly()) {
                result.add("Property opendma:Aspect ReadOnly must be 'true'");
            }
            if(propAspect.getValue() == null) {
                result.add("Property opendma:Aspect is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Aspect");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredAspect = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameAspect.equals(pi.getQName())) {
                        if(piDeclaredAspect == null) {
                            piDeclaredAspect = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Aspect");
                        }
                    }
                }
            }
            if(piDeclaredAspect == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Aspect");
            }
            if(piDeclaredAspect != null) {
                if(!"opendma".equals(piDeclaredAspect.getNamespace())) {
                    result.add("Property info for opendma:Aspect in declared properties qname namespace is not 'opendma'");
                }
                if(!"Aspect".equals(piDeclaredAspect.getName())) {
                    result.add("Property info for opendma:Aspect in declared properties qname name is not 'Aspect'");
                }
                if(piDeclaredAspect.getDataType() != 7) {
                    result.add("Property info for opendma:Aspect in declared properties data type is not '7'");
                }
                if(piDeclaredAspect.isMultiValue() != false) {
                    result.add("Property info for opendma:Aspect in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredAspect.isReadOnly() != true) {
                    result.add("Property info for opendma:Aspect in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredAspect.isHidden() != false) {
                    result.add("Property info for opendma:Aspect in declared properties Hidden is not 'false'");
                }
                if(piDeclaredAspect.isRequired() != true) {
                result.add("Property info for opendma:Aspect in declared properties Required is not 'true'");
                }
                if(piDeclaredAspect.isSystem() != true) {
                    result.add("Property info for opendma:Aspect in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllAspect = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameAspect.equals(pi.getQName())) {
                    if(piAllAspect == null) {
                        piAllAspect = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Aspect");
                    }
                }
            }
        }
        if(piAllAspect == null) {
            result.add("All properties in class have no property info object with qname opendma:Aspect");
        }
        if(piAllAspect != null) {
            if(!"opendma".equals(piAllAspect.getNamespace())) {
                result.add("Property info for opendma:Aspect in all properties qname namespace is not 'opendma'");
            }
            if(!"Aspect".equals(piAllAspect.getName())) {
                result.add("Property info for opendma:Aspect in all properties qname name is not 'Aspect'");
            }
            if(piAllAspect.getDataType() != 7) {
                result.add("Property info for opendma:Aspect in all properties data type is not '7'");
            }
            if(piAllAspect.isMultiValue() != false) {
                result.add("Property info for opendma:Aspect in all properties MultiValue is not 'false'");
            }
            if(piAllAspect.isReadOnly() != true) {
                result.add("Property info for opendma:Aspect in all properties ReadOnly is not 'true'");
            }
            if(piAllAspect.isHidden() != false) {
                result.add("Property info for opendma:Aspect in all properties Hidden is not 'false'");
            }
            if(piAllAspect.isRequired() != true) {
                result.add("Property info for opendma:Aspect in all properties Required is not 'true'");
            }
            if(piAllAspect.isSystem() != true) {
                result.add("Property info for opendma:Aspect in all properties System is not 'true'");
            }
        }
        // opendma:Instantiable
        OdmaQName qnameInstantiable = new OdmaQName("opendma","Instantiable");
        try {
            OdmaProperty propInstantiable = obj.getProperty(qnameInstantiable);
            if(propInstantiable.getName() == null) {
                result.add("Property opendma:Instantiable qname is null");
            }
            if(!"opendma".equals(propInstantiable.getName().getNamespace())) {
                result.add("Property opendma:Instantiable qname namespace is not 'opendma', found instead'"+propInstantiable.getName().getNamespace()+"'");
            }
            if(!"Instantiable".equals(propInstantiable.getName().getName())) {
                result.add("Property opendma:Instantiable qname name is not 'Instantiable', found instead'"+propInstantiable.getName().getName()+"'");
            }
            if(propInstantiable.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:Instantiable type is not 'BOOLEAN'");
            }
            if(propInstantiable.isMultiValue() != false) {
                result.add("Property opendma:Instantiable MultiValue is not 'false'");
            }
            if(propInstantiable.getValue() == null) {
                result.add("Property opendma:Instantiable is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Instantiable");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredInstantiable = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameInstantiable.equals(pi.getQName())) {
                        if(piDeclaredInstantiable == null) {
                            piDeclaredInstantiable = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Instantiable");
                        }
                    }
                }
            }
            if(piDeclaredInstantiable == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Instantiable");
            }
            if(piDeclaredInstantiable != null) {
                if(!"opendma".equals(piDeclaredInstantiable.getNamespace())) {
                    result.add("Property info for opendma:Instantiable in declared properties qname namespace is not 'opendma'");
                }
                if(!"Instantiable".equals(piDeclaredInstantiable.getName())) {
                    result.add("Property info for opendma:Instantiable in declared properties qname name is not 'Instantiable'");
                }
                if(piDeclaredInstantiable.getDataType() != 7) {
                    result.add("Property info for opendma:Instantiable in declared properties data type is not '7'");
                }
                if(piDeclaredInstantiable.isMultiValue() != false) {
                    result.add("Property info for opendma:Instantiable in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredInstantiable.isReadOnly() != false) {
                    result.add("Property info for opendma:Instantiable in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredInstantiable.isHidden() != false) {
                    result.add("Property info for opendma:Instantiable in declared properties Hidden is not 'false'");
                }
                if(piDeclaredInstantiable.isRequired() != true) {
                result.add("Property info for opendma:Instantiable in declared properties Required is not 'true'");
                }
                if(piDeclaredInstantiable.isSystem() != true) {
                    result.add("Property info for opendma:Instantiable in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllInstantiable = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameInstantiable.equals(pi.getQName())) {
                    if(piAllInstantiable == null) {
                        piAllInstantiable = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Instantiable");
                    }
                }
            }
        }
        if(piAllInstantiable == null) {
            result.add("All properties in class have no property info object with qname opendma:Instantiable");
        }
        if(piAllInstantiable != null) {
            if(!"opendma".equals(piAllInstantiable.getNamespace())) {
                result.add("Property info for opendma:Instantiable in all properties qname namespace is not 'opendma'");
            }
            if(!"Instantiable".equals(piAllInstantiable.getName())) {
                result.add("Property info for opendma:Instantiable in all properties qname name is not 'Instantiable'");
            }
            if(piAllInstantiable.getDataType() != 7) {
                result.add("Property info for opendma:Instantiable in all properties data type is not '7'");
            }
            if(piAllInstantiable.isMultiValue() != false) {
                result.add("Property info for opendma:Instantiable in all properties MultiValue is not 'false'");
            }
            if(piAllInstantiable.isReadOnly() != false) {
                result.add("Property info for opendma:Instantiable in all properties ReadOnly is not 'false'");
            }
            if(piAllInstantiable.isHidden() != false) {
                result.add("Property info for opendma:Instantiable in all properties Hidden is not 'false'");
            }
            if(piAllInstantiable.isRequired() != true) {
                result.add("Property info for opendma:Instantiable in all properties Required is not 'true'");
            }
            if(piAllInstantiable.isSystem() != true) {
                result.add("Property info for opendma:Instantiable in all properties System is not 'true'");
            }
        }
        // opendma:Hidden
        OdmaQName qnameHidden = new OdmaQName("opendma","Hidden");
        try {
            OdmaProperty propHidden = obj.getProperty(qnameHidden);
            if(propHidden.getName() == null) {
                result.add("Property opendma:Hidden qname is null");
            }
            if(!"opendma".equals(propHidden.getName().getNamespace())) {
                result.add("Property opendma:Hidden qname namespace is not 'opendma', found instead'"+propHidden.getName().getNamespace()+"'");
            }
            if(!"Hidden".equals(propHidden.getName().getName())) {
                result.add("Property opendma:Hidden qname name is not 'Hidden', found instead'"+propHidden.getName().getName()+"'");
            }
            if(propHidden.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:Hidden type is not 'BOOLEAN'");
            }
            if(propHidden.isMultiValue() != false) {
                result.add("Property opendma:Hidden MultiValue is not 'false'");
            }
            if(propHidden.getValue() == null) {
                result.add("Property opendma:Hidden is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Hidden");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredHidden = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameHidden.equals(pi.getQName())) {
                        if(piDeclaredHidden == null) {
                            piDeclaredHidden = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Hidden");
                        }
                    }
                }
            }
            if(piDeclaredHidden == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Hidden");
            }
            if(piDeclaredHidden != null) {
                if(!"opendma".equals(piDeclaredHidden.getNamespace())) {
                    result.add("Property info for opendma:Hidden in declared properties qname namespace is not 'opendma'");
                }
                if(!"Hidden".equals(piDeclaredHidden.getName())) {
                    result.add("Property info for opendma:Hidden in declared properties qname name is not 'Hidden'");
                }
                if(piDeclaredHidden.getDataType() != 7) {
                    result.add("Property info for opendma:Hidden in declared properties data type is not '7'");
                }
                if(piDeclaredHidden.isMultiValue() != false) {
                    result.add("Property info for opendma:Hidden in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredHidden.isReadOnly() != false) {
                    result.add("Property info for opendma:Hidden in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredHidden.isHidden() != false) {
                    result.add("Property info for opendma:Hidden in declared properties Hidden is not 'false'");
                }
                if(piDeclaredHidden.isRequired() != true) {
                result.add("Property info for opendma:Hidden in declared properties Required is not 'true'");
                }
                if(piDeclaredHidden.isSystem() != true) {
                    result.add("Property info for opendma:Hidden in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllHidden = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameHidden.equals(pi.getQName())) {
                    if(piAllHidden == null) {
                        piAllHidden = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Hidden");
                    }
                }
            }
        }
        if(piAllHidden == null) {
            result.add("All properties in class have no property info object with qname opendma:Hidden");
        }
        if(piAllHidden != null) {
            if(!"opendma".equals(piAllHidden.getNamespace())) {
                result.add("Property info for opendma:Hidden in all properties qname namespace is not 'opendma'");
            }
            if(!"Hidden".equals(piAllHidden.getName())) {
                result.add("Property info for opendma:Hidden in all properties qname name is not 'Hidden'");
            }
            if(piAllHidden.getDataType() != 7) {
                result.add("Property info for opendma:Hidden in all properties data type is not '7'");
            }
            if(piAllHidden.isMultiValue() != false) {
                result.add("Property info for opendma:Hidden in all properties MultiValue is not 'false'");
            }
            if(piAllHidden.isReadOnly() != false) {
                result.add("Property info for opendma:Hidden in all properties ReadOnly is not 'false'");
            }
            if(piAllHidden.isHidden() != false) {
                result.add("Property info for opendma:Hidden in all properties Hidden is not 'false'");
            }
            if(piAllHidden.isRequired() != true) {
                result.add("Property info for opendma:Hidden in all properties Required is not 'true'");
            }
            if(piAllHidden.isSystem() != true) {
                result.add("Property info for opendma:Hidden in all properties System is not 'true'");
            }
        }
        // opendma:System
        OdmaQName qnameSystem = new OdmaQName("opendma","System");
        try {
            OdmaProperty propSystem = obj.getProperty(qnameSystem);
            if(propSystem.getName() == null) {
                result.add("Property opendma:System qname is null");
            }
            if(!"opendma".equals(propSystem.getName().getNamespace())) {
                result.add("Property opendma:System qname namespace is not 'opendma', found instead'"+propSystem.getName().getNamespace()+"'");
            }
            if(!"System".equals(propSystem.getName().getName())) {
                result.add("Property opendma:System qname name is not 'System', found instead'"+propSystem.getName().getName()+"'");
            }
            if(propSystem.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:System type is not 'BOOLEAN'");
            }
            if(propSystem.isMultiValue() != false) {
                result.add("Property opendma:System MultiValue is not 'false'");
            }
            if(propSystem.getValue() == null) {
                result.add("Property opendma:System is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:System");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredSystem = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameSystem.equals(pi.getQName())) {
                        if(piDeclaredSystem == null) {
                            piDeclaredSystem = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:System");
                        }
                    }
                }
            }
            if(piDeclaredSystem == null) {
                result.add("Declared properties in class have no property info object with qname opendma:System");
            }
            if(piDeclaredSystem != null) {
                if(!"opendma".equals(piDeclaredSystem.getNamespace())) {
                    result.add("Property info for opendma:System in declared properties qname namespace is not 'opendma'");
                }
                if(!"System".equals(piDeclaredSystem.getName())) {
                    result.add("Property info for opendma:System in declared properties qname name is not 'System'");
                }
                if(piDeclaredSystem.getDataType() != 7) {
                    result.add("Property info for opendma:System in declared properties data type is not '7'");
                }
                if(piDeclaredSystem.isMultiValue() != false) {
                    result.add("Property info for opendma:System in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredSystem.isReadOnly() != false) {
                    result.add("Property info for opendma:System in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredSystem.isHidden() != false) {
                    result.add("Property info for opendma:System in declared properties Hidden is not 'false'");
                }
                if(piDeclaredSystem.isRequired() != true) {
                result.add("Property info for opendma:System in declared properties Required is not 'true'");
                }
                if(piDeclaredSystem.isSystem() != true) {
                    result.add("Property info for opendma:System in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllSystem = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameSystem.equals(pi.getQName())) {
                    if(piAllSystem == null) {
                        piAllSystem = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:System");
                    }
                }
            }
        }
        if(piAllSystem == null) {
            result.add("All properties in class have no property info object with qname opendma:System");
        }
        if(piAllSystem != null) {
            if(!"opendma".equals(piAllSystem.getNamespace())) {
                result.add("Property info for opendma:System in all properties qname namespace is not 'opendma'");
            }
            if(!"System".equals(piAllSystem.getName())) {
                result.add("Property info for opendma:System in all properties qname name is not 'System'");
            }
            if(piAllSystem.getDataType() != 7) {
                result.add("Property info for opendma:System in all properties data type is not '7'");
            }
            if(piAllSystem.isMultiValue() != false) {
                result.add("Property info for opendma:System in all properties MultiValue is not 'false'");
            }
            if(piAllSystem.isReadOnly() != false) {
                result.add("Property info for opendma:System in all properties ReadOnly is not 'false'");
            }
            if(piAllSystem.isHidden() != false) {
                result.add("Property info for opendma:System in all properties Hidden is not 'false'");
            }
            if(piAllSystem.isRequired() != true) {
                result.add("Property info for opendma:System in all properties Required is not 'true'");
            }
            if(piAllSystem.isSystem() != true) {
                result.add("Property info for opendma:System in all properties System is not 'true'");
            }
        }
        // opendma:Retrievable
        OdmaQName qnameRetrievable = new OdmaQName("opendma","Retrievable");
        try {
            OdmaProperty propRetrievable = obj.getProperty(qnameRetrievable);
            if(propRetrievable.getName() == null) {
                result.add("Property opendma:Retrievable qname is null");
            }
            if(!"opendma".equals(propRetrievable.getName().getNamespace())) {
                result.add("Property opendma:Retrievable qname namespace is not 'opendma', found instead'"+propRetrievable.getName().getNamespace()+"'");
            }
            if(!"Retrievable".equals(propRetrievable.getName().getName())) {
                result.add("Property opendma:Retrievable qname name is not 'Retrievable', found instead'"+propRetrievable.getName().getName()+"'");
            }
            if(propRetrievable.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:Retrievable type is not 'BOOLEAN'");
            }
            if(propRetrievable.isMultiValue() != false) {
                result.add("Property opendma:Retrievable MultiValue is not 'false'");
            }
            if(!propRetrievable.isReadOnly()) {
                result.add("Property opendma:Retrievable ReadOnly must be 'true'");
            }
            if(propRetrievable.getValue() == null) {
                result.add("Property opendma:Retrievable is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Retrievable");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredRetrievable = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameRetrievable.equals(pi.getQName())) {
                        if(piDeclaredRetrievable == null) {
                            piDeclaredRetrievable = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Retrievable");
                        }
                    }
                }
            }
            if(piDeclaredRetrievable == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Retrievable");
            }
            if(piDeclaredRetrievable != null) {
                if(!"opendma".equals(piDeclaredRetrievable.getNamespace())) {
                    result.add("Property info for opendma:Retrievable in declared properties qname namespace is not 'opendma'");
                }
                if(!"Retrievable".equals(piDeclaredRetrievable.getName())) {
                    result.add("Property info for opendma:Retrievable in declared properties qname name is not 'Retrievable'");
                }
                if(piDeclaredRetrievable.getDataType() != 7) {
                    result.add("Property info for opendma:Retrievable in declared properties data type is not '7'");
                }
                if(piDeclaredRetrievable.isMultiValue() != false) {
                    result.add("Property info for opendma:Retrievable in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredRetrievable.isReadOnly() != true) {
                    result.add("Property info for opendma:Retrievable in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredRetrievable.isHidden() != false) {
                    result.add("Property info for opendma:Retrievable in declared properties Hidden is not 'false'");
                }
                if(piDeclaredRetrievable.isRequired() != true) {
                result.add("Property info for opendma:Retrievable in declared properties Required is not 'true'");
                }
                if(piDeclaredRetrievable.isSystem() != true) {
                    result.add("Property info for opendma:Retrievable in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllRetrievable = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameRetrievable.equals(pi.getQName())) {
                    if(piAllRetrievable == null) {
                        piAllRetrievable = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Retrievable");
                    }
                }
            }
        }
        if(piAllRetrievable == null) {
            result.add("All properties in class have no property info object with qname opendma:Retrievable");
        }
        if(piAllRetrievable != null) {
            if(!"opendma".equals(piAllRetrievable.getNamespace())) {
                result.add("Property info for opendma:Retrievable in all properties qname namespace is not 'opendma'");
            }
            if(!"Retrievable".equals(piAllRetrievable.getName())) {
                result.add("Property info for opendma:Retrievable in all properties qname name is not 'Retrievable'");
            }
            if(piAllRetrievable.getDataType() != 7) {
                result.add("Property info for opendma:Retrievable in all properties data type is not '7'");
            }
            if(piAllRetrievable.isMultiValue() != false) {
                result.add("Property info for opendma:Retrievable in all properties MultiValue is not 'false'");
            }
            if(piAllRetrievable.isReadOnly() != true) {
                result.add("Property info for opendma:Retrievable in all properties ReadOnly is not 'true'");
            }
            if(piAllRetrievable.isHidden() != false) {
                result.add("Property info for opendma:Retrievable in all properties Hidden is not 'false'");
            }
            if(piAllRetrievable.isRequired() != true) {
                result.add("Property info for opendma:Retrievable in all properties Required is not 'true'");
            }
            if(piAllRetrievable.isSystem() != true) {
                result.add("Property info for opendma:Retrievable in all properties System is not 'true'");
            }
        }
        // opendma:Searchable
        OdmaQName qnameSearchable = new OdmaQName("opendma","Searchable");
        try {
            OdmaProperty propSearchable = obj.getProperty(qnameSearchable);
            if(propSearchable.getName() == null) {
                result.add("Property opendma:Searchable qname is null");
            }
            if(!"opendma".equals(propSearchable.getName().getNamespace())) {
                result.add("Property opendma:Searchable qname namespace is not 'opendma', found instead'"+propSearchable.getName().getNamespace()+"'");
            }
            if(!"Searchable".equals(propSearchable.getName().getName())) {
                result.add("Property opendma:Searchable qname name is not 'Searchable', found instead'"+propSearchable.getName().getName()+"'");
            }
            if(propSearchable.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:Searchable type is not 'BOOLEAN'");
            }
            if(propSearchable.isMultiValue() != false) {
                result.add("Property opendma:Searchable MultiValue is not 'false'");
            }
            if(!propSearchable.isReadOnly()) {
                result.add("Property opendma:Searchable ReadOnly must be 'true'");
            }
            if(propSearchable.getValue() == null) {
                result.add("Property opendma:Searchable is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Searchable");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredSearchable = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameSearchable.equals(pi.getQName())) {
                        if(piDeclaredSearchable == null) {
                            piDeclaredSearchable = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Searchable");
                        }
                    }
                }
            }
            if(piDeclaredSearchable == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Searchable");
            }
            if(piDeclaredSearchable != null) {
                if(!"opendma".equals(piDeclaredSearchable.getNamespace())) {
                    result.add("Property info for opendma:Searchable in declared properties qname namespace is not 'opendma'");
                }
                if(!"Searchable".equals(piDeclaredSearchable.getName())) {
                    result.add("Property info for opendma:Searchable in declared properties qname name is not 'Searchable'");
                }
                if(piDeclaredSearchable.getDataType() != 7) {
                    result.add("Property info for opendma:Searchable in declared properties data type is not '7'");
                }
                if(piDeclaredSearchable.isMultiValue() != false) {
                    result.add("Property info for opendma:Searchable in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredSearchable.isReadOnly() != true) {
                    result.add("Property info for opendma:Searchable in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredSearchable.isHidden() != false) {
                    result.add("Property info for opendma:Searchable in declared properties Hidden is not 'false'");
                }
                if(piDeclaredSearchable.isRequired() != true) {
                result.add("Property info for opendma:Searchable in declared properties Required is not 'true'");
                }
                if(piDeclaredSearchable.isSystem() != true) {
                    result.add("Property info for opendma:Searchable in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllSearchable = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameSearchable.equals(pi.getQName())) {
                    if(piAllSearchable == null) {
                        piAllSearchable = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Searchable");
                    }
                }
            }
        }
        if(piAllSearchable == null) {
            result.add("All properties in class have no property info object with qname opendma:Searchable");
        }
        if(piAllSearchable != null) {
            if(!"opendma".equals(piAllSearchable.getNamespace())) {
                result.add("Property info for opendma:Searchable in all properties qname namespace is not 'opendma'");
            }
            if(!"Searchable".equals(piAllSearchable.getName())) {
                result.add("Property info for opendma:Searchable in all properties qname name is not 'Searchable'");
            }
            if(piAllSearchable.getDataType() != 7) {
                result.add("Property info for opendma:Searchable in all properties data type is not '7'");
            }
            if(piAllSearchable.isMultiValue() != false) {
                result.add("Property info for opendma:Searchable in all properties MultiValue is not 'false'");
            }
            if(piAllSearchable.isReadOnly() != true) {
                result.add("Property info for opendma:Searchable in all properties ReadOnly is not 'true'");
            }
            if(piAllSearchable.isHidden() != false) {
                result.add("Property info for opendma:Searchable in all properties Hidden is not 'false'");
            }
            if(piAllSearchable.isRequired() != true) {
                result.add("Property info for opendma:Searchable in all properties Required is not 'true'");
            }
            if(piAllSearchable.isSystem() != true) {
                result.add("Property info for opendma:Searchable in all properties System is not 'true'");
            }
        }
        // opendma:SubClasses
        OdmaQName qnameSubClasses = new OdmaQName("opendma","SubClasses");
        try {
            OdmaProperty propSubClasses = obj.getProperty(qnameSubClasses);
            if(propSubClasses.getName() == null) {
                result.add("Property opendma:SubClasses qname is null");
            }
            if(!"opendma".equals(propSubClasses.getName().getNamespace())) {
                result.add("Property opendma:SubClasses qname namespace is not 'opendma', found instead'"+propSubClasses.getName().getNamespace()+"'");
            }
            if(!"SubClasses".equals(propSubClasses.getName().getName())) {
                result.add("Property opendma:SubClasses qname name is not 'SubClasses', found instead'"+propSubClasses.getName().getName()+"'");
            }
            if(propSubClasses.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:SubClasses type is not 'REFERENCE'");
            }
            if(propSubClasses.isMultiValue() != true) {
                result.add("Property opendma:SubClasses MultiValue is not 'true'");
            }
            if(!propSubClasses.isReadOnly()) {
                result.add("Property opendma:SubClasses ReadOnly must be 'true'");
            }
            if(propSubClasses.getValue() == null) {
                result.add("Property opendma:SubClasses is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:SubClasses");
        }
        if(clazz != null && (new OdmaQName("opendma","Class")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredSubClasses = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameSubClasses.equals(pi.getQName())) {
                        if(piDeclaredSubClasses == null) {
                            piDeclaredSubClasses = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:SubClasses");
                        }
                    }
                }
            }
            if(piDeclaredSubClasses == null) {
                result.add("Declared properties in class have no property info object with qname opendma:SubClasses");
            }
            if(piDeclaredSubClasses != null) {
                if(!"opendma".equals(piDeclaredSubClasses.getNamespace())) {
                    result.add("Property info for opendma:SubClasses in declared properties qname namespace is not 'opendma'");
                }
                if(!"SubClasses".equals(piDeclaredSubClasses.getName())) {
                    result.add("Property info for opendma:SubClasses in declared properties qname name is not 'SubClasses'");
                }
                if(piDeclaredSubClasses.getDataType() != 10) {
                    result.add("Property info for opendma:SubClasses in declared properties data type is not '10'");
                }
                if(piDeclaredSubClasses.isMultiValue() != true) {
                    result.add("Property info for opendma:SubClasses in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredSubClasses.isReadOnly() != true) {
                    result.add("Property info for opendma:SubClasses in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Class")).equals(piDeclaredSubClasses.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:SubClasses in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredSubClasses.isHidden() != false) {
                    result.add("Property info for opendma:SubClasses in declared properties Hidden is not 'false'");
                }
                if(piDeclaredSubClasses.isRequired() != false) {
                result.add("Property info for opendma:SubClasses in declared properties Required is not 'false'");
                }
                if(piDeclaredSubClasses.isSystem() != true) {
                    result.add("Property info for opendma:SubClasses in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllSubClasses = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameSubClasses.equals(pi.getQName())) {
                    if(piAllSubClasses == null) {
                        piAllSubClasses = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:SubClasses");
                    }
                }
            }
        }
        if(piAllSubClasses == null) {
            result.add("All properties in class have no property info object with qname opendma:SubClasses");
        }
        if(piAllSubClasses != null) {
            if(!"opendma".equals(piAllSubClasses.getNamespace())) {
                result.add("Property info for opendma:SubClasses in all properties qname namespace is not 'opendma'");
            }
            if(!"SubClasses".equals(piAllSubClasses.getName())) {
                result.add("Property info for opendma:SubClasses in all properties qname name is not 'SubClasses'");
            }
            if(piAllSubClasses.getDataType() != 10) {
                result.add("Property info for opendma:SubClasses in all properties data type is not '10'");
            }
            if(piAllSubClasses.isMultiValue() != true) {
                result.add("Property info for opendma:SubClasses in all properties MultiValue is not 'true'");
            }
            if(piAllSubClasses.isReadOnly() != true) {
                result.add("Property info for opendma:SubClasses in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Class")).equals(piAllSubClasses.getReferenceClass().getQName())) {
                result.add("Property info for opendma:SubClasses in all properties ReadOnly is not 'true'");
            }
            if(piAllSubClasses.isHidden() != false) {
                result.add("Property info for opendma:SubClasses in all properties Hidden is not 'false'");
            }
            if(piAllSubClasses.isRequired() != false) {
                result.add("Property info for opendma:SubClasses in all properties Required is not 'false'");
            }
            if(piAllSubClasses.isSystem() != true) {
                result.add("Property info for opendma:SubClasses in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaPropertyInfo(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaPropertyInfo)) {
            result.add("Does not implement OdmaPropertyInfo interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Name
        OdmaQName qnameName = new OdmaQName("opendma","Name");
        try {
            OdmaProperty propName = obj.getProperty(qnameName);
            if(propName.getName() == null) {
                result.add("Property opendma:Name qname is null");
            }
            if(!"opendma".equals(propName.getName().getNamespace())) {
                result.add("Property opendma:Name qname namespace is not 'opendma', found instead'"+propName.getName().getNamespace()+"'");
            }
            if(!"Name".equals(propName.getName().getName())) {
                result.add("Property opendma:Name qname name is not 'Name', found instead'"+propName.getName().getName()+"'");
            }
            if(propName.getType() != OdmaType.STRING) {
                result.add("Property opendma:Name type is not 'STRING'");
            }
            if(propName.isMultiValue() != false) {
                result.add("Property opendma:Name MultiValue is not 'false'");
            }
            if(propName.getValue() == null) {
                result.add("Property opendma:Name is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Name");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameName.equals(pi.getQName())) {
                        if(piDeclaredName == null) {
                            piDeclaredName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Name");
                        }
                    }
                }
            }
            if(piDeclaredName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Name");
            }
            if(piDeclaredName != null) {
                if(!"opendma".equals(piDeclaredName.getNamespace())) {
                    result.add("Property info for opendma:Name in declared properties qname namespace is not 'opendma'");
                }
                if(!"Name".equals(piDeclaredName.getName())) {
                    result.add("Property info for opendma:Name in declared properties qname name is not 'Name'");
                }
                if(piDeclaredName.getDataType() != 1) {
                    result.add("Property info for opendma:Name in declared properties data type is not '1'");
                }
                if(piDeclaredName.isMultiValue() != false) {
                    result.add("Property info for opendma:Name in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredName.isReadOnly() != false) {
                    result.add("Property info for opendma:Name in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredName.isHidden() != false) {
                    result.add("Property info for opendma:Name in declared properties Hidden is not 'false'");
                }
                if(piDeclaredName.isRequired() != true) {
                result.add("Property info for opendma:Name in declared properties Required is not 'true'");
                }
                if(piDeclaredName.isSystem() != true) {
                    result.add("Property info for opendma:Name in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameName.equals(pi.getQName())) {
                    if(piAllName == null) {
                        piAllName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Name");
                    }
                }
            }
        }
        if(piAllName == null) {
            result.add("All properties in class have no property info object with qname opendma:Name");
        }
        if(piAllName != null) {
            if(!"opendma".equals(piAllName.getNamespace())) {
                result.add("Property info for opendma:Name in all properties qname namespace is not 'opendma'");
            }
            if(!"Name".equals(piAllName.getName())) {
                result.add("Property info for opendma:Name in all properties qname name is not 'Name'");
            }
            if(piAllName.getDataType() != 1) {
                result.add("Property info for opendma:Name in all properties data type is not '1'");
            }
            if(piAllName.isMultiValue() != false) {
                result.add("Property info for opendma:Name in all properties MultiValue is not 'false'");
            }
            if(piAllName.isReadOnly() != false) {
                result.add("Property info for opendma:Name in all properties ReadOnly is not 'false'");
            }
            if(piAllName.isHidden() != false) {
                result.add("Property info for opendma:Name in all properties Hidden is not 'false'");
            }
            if(piAllName.isRequired() != true) {
                result.add("Property info for opendma:Name in all properties Required is not 'true'");
            }
            if(piAllName.isSystem() != true) {
                result.add("Property info for opendma:Name in all properties System is not 'true'");
            }
        }
        // opendma:Namespace
        OdmaQName qnameNamespace = new OdmaQName("opendma","Namespace");
        try {
            OdmaProperty propNamespace = obj.getProperty(qnameNamespace);
            if(propNamespace.getName() == null) {
                result.add("Property opendma:Namespace qname is null");
            }
            if(!"opendma".equals(propNamespace.getName().getNamespace())) {
                result.add("Property opendma:Namespace qname namespace is not 'opendma', found instead'"+propNamespace.getName().getNamespace()+"'");
            }
            if(!"Namespace".equals(propNamespace.getName().getName())) {
                result.add("Property opendma:Namespace qname name is not 'Namespace', found instead'"+propNamespace.getName().getName()+"'");
            }
            if(propNamespace.getType() != OdmaType.STRING) {
                result.add("Property opendma:Namespace type is not 'STRING'");
            }
            if(propNamespace.isMultiValue() != false) {
                result.add("Property opendma:Namespace MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Namespace");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredNamespace = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameNamespace.equals(pi.getQName())) {
                        if(piDeclaredNamespace == null) {
                            piDeclaredNamespace = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Namespace");
                        }
                    }
                }
            }
            if(piDeclaredNamespace == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Namespace");
            }
            if(piDeclaredNamespace != null) {
                if(!"opendma".equals(piDeclaredNamespace.getNamespace())) {
                    result.add("Property info for opendma:Namespace in declared properties qname namespace is not 'opendma'");
                }
                if(!"Namespace".equals(piDeclaredNamespace.getName())) {
                    result.add("Property info for opendma:Namespace in declared properties qname name is not 'Namespace'");
                }
                if(piDeclaredNamespace.getDataType() != 1) {
                    result.add("Property info for opendma:Namespace in declared properties data type is not '1'");
                }
                if(piDeclaredNamespace.isMultiValue() != false) {
                    result.add("Property info for opendma:Namespace in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredNamespace.isReadOnly() != false) {
                    result.add("Property info for opendma:Namespace in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredNamespace.isHidden() != false) {
                    result.add("Property info for opendma:Namespace in declared properties Hidden is not 'false'");
                }
                if(piDeclaredNamespace.isRequired() != false) {
                result.add("Property info for opendma:Namespace in declared properties Required is not 'false'");
                }
                if(piDeclaredNamespace.isSystem() != true) {
                    result.add("Property info for opendma:Namespace in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllNamespace = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameNamespace.equals(pi.getQName())) {
                    if(piAllNamespace == null) {
                        piAllNamespace = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Namespace");
                    }
                }
            }
        }
        if(piAllNamespace == null) {
            result.add("All properties in class have no property info object with qname opendma:Namespace");
        }
        if(piAllNamespace != null) {
            if(!"opendma".equals(piAllNamespace.getNamespace())) {
                result.add("Property info for opendma:Namespace in all properties qname namespace is not 'opendma'");
            }
            if(!"Namespace".equals(piAllNamespace.getName())) {
                result.add("Property info for opendma:Namespace in all properties qname name is not 'Namespace'");
            }
            if(piAllNamespace.getDataType() != 1) {
                result.add("Property info for opendma:Namespace in all properties data type is not '1'");
            }
            if(piAllNamespace.isMultiValue() != false) {
                result.add("Property info for opendma:Namespace in all properties MultiValue is not 'false'");
            }
            if(piAllNamespace.isReadOnly() != false) {
                result.add("Property info for opendma:Namespace in all properties ReadOnly is not 'false'");
            }
            if(piAllNamespace.isHidden() != false) {
                result.add("Property info for opendma:Namespace in all properties Hidden is not 'false'");
            }
            if(piAllNamespace.isRequired() != false) {
                result.add("Property info for opendma:Namespace in all properties Required is not 'false'");
            }
            if(piAllNamespace.isSystem() != true) {
                result.add("Property info for opendma:Namespace in all properties System is not 'true'");
            }
        }
        // opendma:DisplayName
        OdmaQName qnameDisplayName = new OdmaQName("opendma","DisplayName");
        try {
            OdmaProperty propDisplayName = obj.getProperty(qnameDisplayName);
            if(propDisplayName.getName() == null) {
                result.add("Property opendma:DisplayName qname is null");
            }
            if(!"opendma".equals(propDisplayName.getName().getNamespace())) {
                result.add("Property opendma:DisplayName qname namespace is not 'opendma', found instead'"+propDisplayName.getName().getNamespace()+"'");
            }
            if(!"DisplayName".equals(propDisplayName.getName().getName())) {
                result.add("Property opendma:DisplayName qname name is not 'DisplayName', found instead'"+propDisplayName.getName().getName()+"'");
            }
            if(propDisplayName.getType() != OdmaType.STRING) {
                result.add("Property opendma:DisplayName type is not 'STRING'");
            }
            if(propDisplayName.isMultiValue() != false) {
                result.add("Property opendma:DisplayName MultiValue is not 'false'");
            }
            if(propDisplayName.getValue() == null) {
                result.add("Property opendma:DisplayName is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DisplayName");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDisplayName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDisplayName.equals(pi.getQName())) {
                        if(piDeclaredDisplayName == null) {
                            piDeclaredDisplayName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DisplayName");
                        }
                    }
                }
            }
            if(piDeclaredDisplayName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DisplayName");
            }
            if(piDeclaredDisplayName != null) {
                if(!"opendma".equals(piDeclaredDisplayName.getNamespace())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname namespace is not 'opendma'");
                }
                if(!"DisplayName".equals(piDeclaredDisplayName.getName())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname name is not 'DisplayName'");
                }
                if(piDeclaredDisplayName.getDataType() != 1) {
                    result.add("Property info for opendma:DisplayName in declared properties data type is not '1'");
                }
                if(piDeclaredDisplayName.isMultiValue() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredDisplayName.isReadOnly() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDisplayName.isHidden() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in declared properties Required is not 'true'");
                }
                if(piDeclaredDisplayName.isSystem() != true) {
                    result.add("Property info for opendma:DisplayName in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDisplayName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDisplayName.equals(pi.getQName())) {
                    if(piAllDisplayName == null) {
                        piAllDisplayName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DisplayName");
                    }
                }
            }
        }
        if(piAllDisplayName == null) {
            result.add("All properties in class have no property info object with qname opendma:DisplayName");
        }
        if(piAllDisplayName != null) {
            if(!"opendma".equals(piAllDisplayName.getNamespace())) {
                result.add("Property info for opendma:DisplayName in all properties qname namespace is not 'opendma'");
            }
            if(!"DisplayName".equals(piAllDisplayName.getName())) {
                result.add("Property info for opendma:DisplayName in all properties qname name is not 'DisplayName'");
            }
            if(piAllDisplayName.getDataType() != 1) {
                result.add("Property info for opendma:DisplayName in all properties data type is not '1'");
            }
            if(piAllDisplayName.isMultiValue() != false) {
                result.add("Property info for opendma:DisplayName in all properties MultiValue is not 'false'");
            }
            if(piAllDisplayName.isReadOnly() != false) {
                result.add("Property info for opendma:DisplayName in all properties ReadOnly is not 'false'");
            }
            if(piAllDisplayName.isHidden() != false) {
                result.add("Property info for opendma:DisplayName in all properties Hidden is not 'false'");
            }
            if(piAllDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in all properties Required is not 'true'");
            }
            if(piAllDisplayName.isSystem() != true) {
                result.add("Property info for opendma:DisplayName in all properties System is not 'true'");
            }
        }
        // opendma:DataType
        OdmaQName qnameDataType = new OdmaQName("opendma","DataType");
        try {
            OdmaProperty propDataType = obj.getProperty(qnameDataType);
            if(propDataType.getName() == null) {
                result.add("Property opendma:DataType qname is null");
            }
            if(!"opendma".equals(propDataType.getName().getNamespace())) {
                result.add("Property opendma:DataType qname namespace is not 'opendma', found instead'"+propDataType.getName().getNamespace()+"'");
            }
            if(!"DataType".equals(propDataType.getName().getName())) {
                result.add("Property opendma:DataType qname name is not 'DataType', found instead'"+propDataType.getName().getName()+"'");
            }
            if(propDataType.getType() != OdmaType.INTEGER) {
                result.add("Property opendma:DataType type is not 'INTEGER'");
            }
            if(propDataType.isMultiValue() != false) {
                result.add("Property opendma:DataType MultiValue is not 'false'");
            }
            if(propDataType.getValue() == null) {
                result.add("Property opendma:DataType is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DataType");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDataType = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDataType.equals(pi.getQName())) {
                        if(piDeclaredDataType == null) {
                            piDeclaredDataType = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DataType");
                        }
                    }
                }
            }
            if(piDeclaredDataType == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DataType");
            }
            if(piDeclaredDataType != null) {
                if(!"opendma".equals(piDeclaredDataType.getNamespace())) {
                    result.add("Property info for opendma:DataType in declared properties qname namespace is not 'opendma'");
                }
                if(!"DataType".equals(piDeclaredDataType.getName())) {
                    result.add("Property info for opendma:DataType in declared properties qname name is not 'DataType'");
                }
                if(piDeclaredDataType.getDataType() != 2) {
                    result.add("Property info for opendma:DataType in declared properties data type is not '2'");
                }
                if(piDeclaredDataType.isMultiValue() != false) {
                    result.add("Property info for opendma:DataType in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredDataType.isReadOnly() != false) {
                    result.add("Property info for opendma:DataType in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDataType.isHidden() != false) {
                    result.add("Property info for opendma:DataType in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDataType.isRequired() != true) {
                result.add("Property info for opendma:DataType in declared properties Required is not 'true'");
                }
                if(piDeclaredDataType.isSystem() != true) {
                    result.add("Property info for opendma:DataType in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDataType = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDataType.equals(pi.getQName())) {
                    if(piAllDataType == null) {
                        piAllDataType = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DataType");
                    }
                }
            }
        }
        if(piAllDataType == null) {
            result.add("All properties in class have no property info object with qname opendma:DataType");
        }
        if(piAllDataType != null) {
            if(!"opendma".equals(piAllDataType.getNamespace())) {
                result.add("Property info for opendma:DataType in all properties qname namespace is not 'opendma'");
            }
            if(!"DataType".equals(piAllDataType.getName())) {
                result.add("Property info for opendma:DataType in all properties qname name is not 'DataType'");
            }
            if(piAllDataType.getDataType() != 2) {
                result.add("Property info for opendma:DataType in all properties data type is not '2'");
            }
            if(piAllDataType.isMultiValue() != false) {
                result.add("Property info for opendma:DataType in all properties MultiValue is not 'false'");
            }
            if(piAllDataType.isReadOnly() != false) {
                result.add("Property info for opendma:DataType in all properties ReadOnly is not 'false'");
            }
            if(piAllDataType.isHidden() != false) {
                result.add("Property info for opendma:DataType in all properties Hidden is not 'false'");
            }
            if(piAllDataType.isRequired() != true) {
                result.add("Property info for opendma:DataType in all properties Required is not 'true'");
            }
            if(piAllDataType.isSystem() != true) {
                result.add("Property info for opendma:DataType in all properties System is not 'true'");
            }
        }
        // opendma:ReferenceClass
        OdmaQName qnameReferenceClass = new OdmaQName("opendma","ReferenceClass");
        try {
            OdmaProperty propReferenceClass = obj.getProperty(qnameReferenceClass);
            if(propReferenceClass.getName() == null) {
                result.add("Property opendma:ReferenceClass qname is null");
            }
            if(!"opendma".equals(propReferenceClass.getName().getNamespace())) {
                result.add("Property opendma:ReferenceClass qname namespace is not 'opendma', found instead'"+propReferenceClass.getName().getNamespace()+"'");
            }
            if(!"ReferenceClass".equals(propReferenceClass.getName().getName())) {
                result.add("Property opendma:ReferenceClass qname name is not 'ReferenceClass', found instead'"+propReferenceClass.getName().getName()+"'");
            }
            if(propReferenceClass.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:ReferenceClass type is not 'REFERENCE'");
            }
            if(propReferenceClass.isMultiValue() != false) {
                result.add("Property opendma:ReferenceClass MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ReferenceClass");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredReferenceClass = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameReferenceClass.equals(pi.getQName())) {
                        if(piDeclaredReferenceClass == null) {
                            piDeclaredReferenceClass = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ReferenceClass");
                        }
                    }
                }
            }
            if(piDeclaredReferenceClass == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ReferenceClass");
            }
            if(piDeclaredReferenceClass != null) {
                if(!"opendma".equals(piDeclaredReferenceClass.getNamespace())) {
                    result.add("Property info for opendma:ReferenceClass in declared properties qname namespace is not 'opendma'");
                }
                if(!"ReferenceClass".equals(piDeclaredReferenceClass.getName())) {
                    result.add("Property info for opendma:ReferenceClass in declared properties qname name is not 'ReferenceClass'");
                }
                if(piDeclaredReferenceClass.getDataType() != 10) {
                    result.add("Property info for opendma:ReferenceClass in declared properties data type is not '10'");
                }
                if(piDeclaredReferenceClass.isMultiValue() != false) {
                    result.add("Property info for opendma:ReferenceClass in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredReferenceClass.isReadOnly() != false) {
                    result.add("Property info for opendma:ReferenceClass in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","Class")).equals(piDeclaredReferenceClass.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:ReferenceClass in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredReferenceClass.isHidden() != false) {
                    result.add("Property info for opendma:ReferenceClass in declared properties Hidden is not 'false'");
                }
                if(piDeclaredReferenceClass.isRequired() != false) {
                result.add("Property info for opendma:ReferenceClass in declared properties Required is not 'false'");
                }
                if(piDeclaredReferenceClass.isSystem() != true) {
                    result.add("Property info for opendma:ReferenceClass in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllReferenceClass = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameReferenceClass.equals(pi.getQName())) {
                    if(piAllReferenceClass == null) {
                        piAllReferenceClass = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ReferenceClass");
                    }
                }
            }
        }
        if(piAllReferenceClass == null) {
            result.add("All properties in class have no property info object with qname opendma:ReferenceClass");
        }
        if(piAllReferenceClass != null) {
            if(!"opendma".equals(piAllReferenceClass.getNamespace())) {
                result.add("Property info for opendma:ReferenceClass in all properties qname namespace is not 'opendma'");
            }
            if(!"ReferenceClass".equals(piAllReferenceClass.getName())) {
                result.add("Property info for opendma:ReferenceClass in all properties qname name is not 'ReferenceClass'");
            }
            if(piAllReferenceClass.getDataType() != 10) {
                result.add("Property info for opendma:ReferenceClass in all properties data type is not '10'");
            }
            if(piAllReferenceClass.isMultiValue() != false) {
                result.add("Property info for opendma:ReferenceClass in all properties MultiValue is not 'false'");
            }
            if(piAllReferenceClass.isReadOnly() != false) {
                result.add("Property info for opendma:ReferenceClass in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","Class")).equals(piAllReferenceClass.getReferenceClass().getQName())) {
                result.add("Property info for opendma:ReferenceClass in all properties ReadOnly is not 'false'");
            }
            if(piAllReferenceClass.isHidden() != false) {
                result.add("Property info for opendma:ReferenceClass in all properties Hidden is not 'false'");
            }
            if(piAllReferenceClass.isRequired() != false) {
                result.add("Property info for opendma:ReferenceClass in all properties Required is not 'false'");
            }
            if(piAllReferenceClass.isSystem() != true) {
                result.add("Property info for opendma:ReferenceClass in all properties System is not 'true'");
            }
        }
        // opendma:MultiValue
        OdmaQName qnameMultiValue = new OdmaQName("opendma","MultiValue");
        try {
            OdmaProperty propMultiValue = obj.getProperty(qnameMultiValue);
            if(propMultiValue.getName() == null) {
                result.add("Property opendma:MultiValue qname is null");
            }
            if(!"opendma".equals(propMultiValue.getName().getNamespace())) {
                result.add("Property opendma:MultiValue qname namespace is not 'opendma', found instead'"+propMultiValue.getName().getNamespace()+"'");
            }
            if(!"MultiValue".equals(propMultiValue.getName().getName())) {
                result.add("Property opendma:MultiValue qname name is not 'MultiValue', found instead'"+propMultiValue.getName().getName()+"'");
            }
            if(propMultiValue.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:MultiValue type is not 'BOOLEAN'");
            }
            if(propMultiValue.isMultiValue() != false) {
                result.add("Property opendma:MultiValue MultiValue is not 'false'");
            }
            if(propMultiValue.getValue() == null) {
                result.add("Property opendma:MultiValue is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:MultiValue");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredMultiValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameMultiValue.equals(pi.getQName())) {
                        if(piDeclaredMultiValue == null) {
                            piDeclaredMultiValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:MultiValue");
                        }
                    }
                }
            }
            if(piDeclaredMultiValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:MultiValue");
            }
            if(piDeclaredMultiValue != null) {
                if(!"opendma".equals(piDeclaredMultiValue.getNamespace())) {
                    result.add("Property info for opendma:MultiValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"MultiValue".equals(piDeclaredMultiValue.getName())) {
                    result.add("Property info for opendma:MultiValue in declared properties qname name is not 'MultiValue'");
                }
                if(piDeclaredMultiValue.getDataType() != 7) {
                    result.add("Property info for opendma:MultiValue in declared properties data type is not '7'");
                }
                if(piDeclaredMultiValue.isMultiValue() != false) {
                    result.add("Property info for opendma:MultiValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredMultiValue.isReadOnly() != false) {
                    result.add("Property info for opendma:MultiValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredMultiValue.isHidden() != false) {
                    result.add("Property info for opendma:MultiValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredMultiValue.isRequired() != true) {
                result.add("Property info for opendma:MultiValue in declared properties Required is not 'true'");
                }
                if(piDeclaredMultiValue.isSystem() != true) {
                    result.add("Property info for opendma:MultiValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllMultiValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameMultiValue.equals(pi.getQName())) {
                    if(piAllMultiValue == null) {
                        piAllMultiValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:MultiValue");
                    }
                }
            }
        }
        if(piAllMultiValue == null) {
            result.add("All properties in class have no property info object with qname opendma:MultiValue");
        }
        if(piAllMultiValue != null) {
            if(!"opendma".equals(piAllMultiValue.getNamespace())) {
                result.add("Property info for opendma:MultiValue in all properties qname namespace is not 'opendma'");
            }
            if(!"MultiValue".equals(piAllMultiValue.getName())) {
                result.add("Property info for opendma:MultiValue in all properties qname name is not 'MultiValue'");
            }
            if(piAllMultiValue.getDataType() != 7) {
                result.add("Property info for opendma:MultiValue in all properties data type is not '7'");
            }
            if(piAllMultiValue.isMultiValue() != false) {
                result.add("Property info for opendma:MultiValue in all properties MultiValue is not 'false'");
            }
            if(piAllMultiValue.isReadOnly() != false) {
                result.add("Property info for opendma:MultiValue in all properties ReadOnly is not 'false'");
            }
            if(piAllMultiValue.isHidden() != false) {
                result.add("Property info for opendma:MultiValue in all properties Hidden is not 'false'");
            }
            if(piAllMultiValue.isRequired() != true) {
                result.add("Property info for opendma:MultiValue in all properties Required is not 'true'");
            }
            if(piAllMultiValue.isSystem() != true) {
                result.add("Property info for opendma:MultiValue in all properties System is not 'true'");
            }
        }
        // opendma:Required
        OdmaQName qnameRequired = new OdmaQName("opendma","Required");
        try {
            OdmaProperty propRequired = obj.getProperty(qnameRequired);
            if(propRequired.getName() == null) {
                result.add("Property opendma:Required qname is null");
            }
            if(!"opendma".equals(propRequired.getName().getNamespace())) {
                result.add("Property opendma:Required qname namespace is not 'opendma', found instead'"+propRequired.getName().getNamespace()+"'");
            }
            if(!"Required".equals(propRequired.getName().getName())) {
                result.add("Property opendma:Required qname name is not 'Required', found instead'"+propRequired.getName().getName()+"'");
            }
            if(propRequired.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:Required type is not 'BOOLEAN'");
            }
            if(propRequired.isMultiValue() != false) {
                result.add("Property opendma:Required MultiValue is not 'false'");
            }
            if(propRequired.getValue() == null) {
                result.add("Property opendma:Required is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Required");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredRequired = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameRequired.equals(pi.getQName())) {
                        if(piDeclaredRequired == null) {
                            piDeclaredRequired = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Required");
                        }
                    }
                }
            }
            if(piDeclaredRequired == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Required");
            }
            if(piDeclaredRequired != null) {
                if(!"opendma".equals(piDeclaredRequired.getNamespace())) {
                    result.add("Property info for opendma:Required in declared properties qname namespace is not 'opendma'");
                }
                if(!"Required".equals(piDeclaredRequired.getName())) {
                    result.add("Property info for opendma:Required in declared properties qname name is not 'Required'");
                }
                if(piDeclaredRequired.getDataType() != 7) {
                    result.add("Property info for opendma:Required in declared properties data type is not '7'");
                }
                if(piDeclaredRequired.isMultiValue() != false) {
                    result.add("Property info for opendma:Required in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredRequired.isReadOnly() != false) {
                    result.add("Property info for opendma:Required in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredRequired.isHidden() != false) {
                    result.add("Property info for opendma:Required in declared properties Hidden is not 'false'");
                }
                if(piDeclaredRequired.isRequired() != true) {
                result.add("Property info for opendma:Required in declared properties Required is not 'true'");
                }
                if(piDeclaredRequired.isSystem() != true) {
                    result.add("Property info for opendma:Required in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllRequired = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameRequired.equals(pi.getQName())) {
                    if(piAllRequired == null) {
                        piAllRequired = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Required");
                    }
                }
            }
        }
        if(piAllRequired == null) {
            result.add("All properties in class have no property info object with qname opendma:Required");
        }
        if(piAllRequired != null) {
            if(!"opendma".equals(piAllRequired.getNamespace())) {
                result.add("Property info for opendma:Required in all properties qname namespace is not 'opendma'");
            }
            if(!"Required".equals(piAllRequired.getName())) {
                result.add("Property info for opendma:Required in all properties qname name is not 'Required'");
            }
            if(piAllRequired.getDataType() != 7) {
                result.add("Property info for opendma:Required in all properties data type is not '7'");
            }
            if(piAllRequired.isMultiValue() != false) {
                result.add("Property info for opendma:Required in all properties MultiValue is not 'false'");
            }
            if(piAllRequired.isReadOnly() != false) {
                result.add("Property info for opendma:Required in all properties ReadOnly is not 'false'");
            }
            if(piAllRequired.isHidden() != false) {
                result.add("Property info for opendma:Required in all properties Hidden is not 'false'");
            }
            if(piAllRequired.isRequired() != true) {
                result.add("Property info for opendma:Required in all properties Required is not 'true'");
            }
            if(piAllRequired.isSystem() != true) {
                result.add("Property info for opendma:Required in all properties System is not 'true'");
            }
        }
        // opendma:ReadOnly
        OdmaQName qnameReadOnly = new OdmaQName("opendma","ReadOnly");
        try {
            OdmaProperty propReadOnly = obj.getProperty(qnameReadOnly);
            if(propReadOnly.getName() == null) {
                result.add("Property opendma:ReadOnly qname is null");
            }
            if(!"opendma".equals(propReadOnly.getName().getNamespace())) {
                result.add("Property opendma:ReadOnly qname namespace is not 'opendma', found instead'"+propReadOnly.getName().getNamespace()+"'");
            }
            if(!"ReadOnly".equals(propReadOnly.getName().getName())) {
                result.add("Property opendma:ReadOnly qname name is not 'ReadOnly', found instead'"+propReadOnly.getName().getName()+"'");
            }
            if(propReadOnly.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:ReadOnly type is not 'BOOLEAN'");
            }
            if(propReadOnly.isMultiValue() != false) {
                result.add("Property opendma:ReadOnly MultiValue is not 'false'");
            }
            if(propReadOnly.getValue() == null) {
                result.add("Property opendma:ReadOnly is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ReadOnly");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredReadOnly = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameReadOnly.equals(pi.getQName())) {
                        if(piDeclaredReadOnly == null) {
                            piDeclaredReadOnly = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ReadOnly");
                        }
                    }
                }
            }
            if(piDeclaredReadOnly == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ReadOnly");
            }
            if(piDeclaredReadOnly != null) {
                if(!"opendma".equals(piDeclaredReadOnly.getNamespace())) {
                    result.add("Property info for opendma:ReadOnly in declared properties qname namespace is not 'opendma'");
                }
                if(!"ReadOnly".equals(piDeclaredReadOnly.getName())) {
                    result.add("Property info for opendma:ReadOnly in declared properties qname name is not 'ReadOnly'");
                }
                if(piDeclaredReadOnly.getDataType() != 7) {
                    result.add("Property info for opendma:ReadOnly in declared properties data type is not '7'");
                }
                if(piDeclaredReadOnly.isMultiValue() != false) {
                    result.add("Property info for opendma:ReadOnly in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredReadOnly.isReadOnly() != false) {
                    result.add("Property info for opendma:ReadOnly in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredReadOnly.isHidden() != false) {
                    result.add("Property info for opendma:ReadOnly in declared properties Hidden is not 'false'");
                }
                if(piDeclaredReadOnly.isRequired() != true) {
                result.add("Property info for opendma:ReadOnly in declared properties Required is not 'true'");
                }
                if(piDeclaredReadOnly.isSystem() != true) {
                    result.add("Property info for opendma:ReadOnly in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllReadOnly = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameReadOnly.equals(pi.getQName())) {
                    if(piAllReadOnly == null) {
                        piAllReadOnly = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ReadOnly");
                    }
                }
            }
        }
        if(piAllReadOnly == null) {
            result.add("All properties in class have no property info object with qname opendma:ReadOnly");
        }
        if(piAllReadOnly != null) {
            if(!"opendma".equals(piAllReadOnly.getNamespace())) {
                result.add("Property info for opendma:ReadOnly in all properties qname namespace is not 'opendma'");
            }
            if(!"ReadOnly".equals(piAllReadOnly.getName())) {
                result.add("Property info for opendma:ReadOnly in all properties qname name is not 'ReadOnly'");
            }
            if(piAllReadOnly.getDataType() != 7) {
                result.add("Property info for opendma:ReadOnly in all properties data type is not '7'");
            }
            if(piAllReadOnly.isMultiValue() != false) {
                result.add("Property info for opendma:ReadOnly in all properties MultiValue is not 'false'");
            }
            if(piAllReadOnly.isReadOnly() != false) {
                result.add("Property info for opendma:ReadOnly in all properties ReadOnly is not 'false'");
            }
            if(piAllReadOnly.isHidden() != false) {
                result.add("Property info for opendma:ReadOnly in all properties Hidden is not 'false'");
            }
            if(piAllReadOnly.isRequired() != true) {
                result.add("Property info for opendma:ReadOnly in all properties Required is not 'true'");
            }
            if(piAllReadOnly.isSystem() != true) {
                result.add("Property info for opendma:ReadOnly in all properties System is not 'true'");
            }
        }
        // opendma:Hidden
        OdmaQName qnameHidden = new OdmaQName("opendma","Hidden");
        try {
            OdmaProperty propHidden = obj.getProperty(qnameHidden);
            if(propHidden.getName() == null) {
                result.add("Property opendma:Hidden qname is null");
            }
            if(!"opendma".equals(propHidden.getName().getNamespace())) {
                result.add("Property opendma:Hidden qname namespace is not 'opendma', found instead'"+propHidden.getName().getNamespace()+"'");
            }
            if(!"Hidden".equals(propHidden.getName().getName())) {
                result.add("Property opendma:Hidden qname name is not 'Hidden', found instead'"+propHidden.getName().getName()+"'");
            }
            if(propHidden.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:Hidden type is not 'BOOLEAN'");
            }
            if(propHidden.isMultiValue() != false) {
                result.add("Property opendma:Hidden MultiValue is not 'false'");
            }
            if(propHidden.getValue() == null) {
                result.add("Property opendma:Hidden is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Hidden");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredHidden = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameHidden.equals(pi.getQName())) {
                        if(piDeclaredHidden == null) {
                            piDeclaredHidden = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Hidden");
                        }
                    }
                }
            }
            if(piDeclaredHidden == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Hidden");
            }
            if(piDeclaredHidden != null) {
                if(!"opendma".equals(piDeclaredHidden.getNamespace())) {
                    result.add("Property info for opendma:Hidden in declared properties qname namespace is not 'opendma'");
                }
                if(!"Hidden".equals(piDeclaredHidden.getName())) {
                    result.add("Property info for opendma:Hidden in declared properties qname name is not 'Hidden'");
                }
                if(piDeclaredHidden.getDataType() != 7) {
                    result.add("Property info for opendma:Hidden in declared properties data type is not '7'");
                }
                if(piDeclaredHidden.isMultiValue() != false) {
                    result.add("Property info for opendma:Hidden in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredHidden.isReadOnly() != false) {
                    result.add("Property info for opendma:Hidden in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredHidden.isHidden() != false) {
                    result.add("Property info for opendma:Hidden in declared properties Hidden is not 'false'");
                }
                if(piDeclaredHidden.isRequired() != true) {
                result.add("Property info for opendma:Hidden in declared properties Required is not 'true'");
                }
                if(piDeclaredHidden.isSystem() != true) {
                    result.add("Property info for opendma:Hidden in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllHidden = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameHidden.equals(pi.getQName())) {
                    if(piAllHidden == null) {
                        piAllHidden = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Hidden");
                    }
                }
            }
        }
        if(piAllHidden == null) {
            result.add("All properties in class have no property info object with qname opendma:Hidden");
        }
        if(piAllHidden != null) {
            if(!"opendma".equals(piAllHidden.getNamespace())) {
                result.add("Property info for opendma:Hidden in all properties qname namespace is not 'opendma'");
            }
            if(!"Hidden".equals(piAllHidden.getName())) {
                result.add("Property info for opendma:Hidden in all properties qname name is not 'Hidden'");
            }
            if(piAllHidden.getDataType() != 7) {
                result.add("Property info for opendma:Hidden in all properties data type is not '7'");
            }
            if(piAllHidden.isMultiValue() != false) {
                result.add("Property info for opendma:Hidden in all properties MultiValue is not 'false'");
            }
            if(piAllHidden.isReadOnly() != false) {
                result.add("Property info for opendma:Hidden in all properties ReadOnly is not 'false'");
            }
            if(piAllHidden.isHidden() != false) {
                result.add("Property info for opendma:Hidden in all properties Hidden is not 'false'");
            }
            if(piAllHidden.isRequired() != true) {
                result.add("Property info for opendma:Hidden in all properties Required is not 'true'");
            }
            if(piAllHidden.isSystem() != true) {
                result.add("Property info for opendma:Hidden in all properties System is not 'true'");
            }
        }
        // opendma:System
        OdmaQName qnameSystem = new OdmaQName("opendma","System");
        try {
            OdmaProperty propSystem = obj.getProperty(qnameSystem);
            if(propSystem.getName() == null) {
                result.add("Property opendma:System qname is null");
            }
            if(!"opendma".equals(propSystem.getName().getNamespace())) {
                result.add("Property opendma:System qname namespace is not 'opendma', found instead'"+propSystem.getName().getNamespace()+"'");
            }
            if(!"System".equals(propSystem.getName().getName())) {
                result.add("Property opendma:System qname name is not 'System', found instead'"+propSystem.getName().getName()+"'");
            }
            if(propSystem.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:System type is not 'BOOLEAN'");
            }
            if(propSystem.isMultiValue() != false) {
                result.add("Property opendma:System MultiValue is not 'false'");
            }
            if(propSystem.getValue() == null) {
                result.add("Property opendma:System is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:System");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredSystem = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameSystem.equals(pi.getQName())) {
                        if(piDeclaredSystem == null) {
                            piDeclaredSystem = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:System");
                        }
                    }
                }
            }
            if(piDeclaredSystem == null) {
                result.add("Declared properties in class have no property info object with qname opendma:System");
            }
            if(piDeclaredSystem != null) {
                if(!"opendma".equals(piDeclaredSystem.getNamespace())) {
                    result.add("Property info for opendma:System in declared properties qname namespace is not 'opendma'");
                }
                if(!"System".equals(piDeclaredSystem.getName())) {
                    result.add("Property info for opendma:System in declared properties qname name is not 'System'");
                }
                if(piDeclaredSystem.getDataType() != 7) {
                    result.add("Property info for opendma:System in declared properties data type is not '7'");
                }
                if(piDeclaredSystem.isMultiValue() != false) {
                    result.add("Property info for opendma:System in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredSystem.isReadOnly() != false) {
                    result.add("Property info for opendma:System in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredSystem.isHidden() != false) {
                    result.add("Property info for opendma:System in declared properties Hidden is not 'false'");
                }
                if(piDeclaredSystem.isRequired() != true) {
                result.add("Property info for opendma:System in declared properties Required is not 'true'");
                }
                if(piDeclaredSystem.isSystem() != true) {
                    result.add("Property info for opendma:System in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllSystem = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameSystem.equals(pi.getQName())) {
                    if(piAllSystem == null) {
                        piAllSystem = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:System");
                    }
                }
            }
        }
        if(piAllSystem == null) {
            result.add("All properties in class have no property info object with qname opendma:System");
        }
        if(piAllSystem != null) {
            if(!"opendma".equals(piAllSystem.getNamespace())) {
                result.add("Property info for opendma:System in all properties qname namespace is not 'opendma'");
            }
            if(!"System".equals(piAllSystem.getName())) {
                result.add("Property info for opendma:System in all properties qname name is not 'System'");
            }
            if(piAllSystem.getDataType() != 7) {
                result.add("Property info for opendma:System in all properties data type is not '7'");
            }
            if(piAllSystem.isMultiValue() != false) {
                result.add("Property info for opendma:System in all properties MultiValue is not 'false'");
            }
            if(piAllSystem.isReadOnly() != false) {
                result.add("Property info for opendma:System in all properties ReadOnly is not 'false'");
            }
            if(piAllSystem.isHidden() != false) {
                result.add("Property info for opendma:System in all properties Hidden is not 'false'");
            }
            if(piAllSystem.isRequired() != true) {
                result.add("Property info for opendma:System in all properties Required is not 'true'");
            }
            if(piAllSystem.isSystem() != true) {
                result.add("Property info for opendma:System in all properties System is not 'true'");
            }
        }
        // opendma:Choices
        OdmaQName qnameChoices = new OdmaQName("opendma","Choices");
        try {
            OdmaProperty propChoices = obj.getProperty(qnameChoices);
            if(propChoices.getName() == null) {
                result.add("Property opendma:Choices qname is null");
            }
            if(!"opendma".equals(propChoices.getName().getNamespace())) {
                result.add("Property opendma:Choices qname namespace is not 'opendma', found instead'"+propChoices.getName().getNamespace()+"'");
            }
            if(!"Choices".equals(propChoices.getName().getName())) {
                result.add("Property opendma:Choices qname name is not 'Choices', found instead'"+propChoices.getName().getName()+"'");
            }
            if(propChoices.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Choices type is not 'REFERENCE'");
            }
            if(propChoices.isMultiValue() != true) {
                result.add("Property opendma:Choices MultiValue is not 'true'");
            }
            if(propChoices.getValue() == null) {
                result.add("Property opendma:Choices is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Choices");
        }
        if(clazz != null && (new OdmaQName("opendma","PropertyInfo")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredChoices = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameChoices.equals(pi.getQName())) {
                        if(piDeclaredChoices == null) {
                            piDeclaredChoices = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Choices");
                        }
                    }
                }
            }
            if(piDeclaredChoices == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Choices");
            }
            if(piDeclaredChoices != null) {
                if(!"opendma".equals(piDeclaredChoices.getNamespace())) {
                    result.add("Property info for opendma:Choices in declared properties qname namespace is not 'opendma'");
                }
                if(!"Choices".equals(piDeclaredChoices.getName())) {
                    result.add("Property info for opendma:Choices in declared properties qname name is not 'Choices'");
                }
                if(piDeclaredChoices.getDataType() != 10) {
                    result.add("Property info for opendma:Choices in declared properties data type is not '10'");
                }
                if(piDeclaredChoices.isMultiValue() != true) {
                    result.add("Property info for opendma:Choices in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredChoices.isReadOnly() != false) {
                    result.add("Property info for opendma:Choices in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","ChoiceValue")).equals(piDeclaredChoices.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Choices in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredChoices.isHidden() != false) {
                    result.add("Property info for opendma:Choices in declared properties Hidden is not 'false'");
                }
                if(piDeclaredChoices.isRequired() != false) {
                result.add("Property info for opendma:Choices in declared properties Required is not 'false'");
                }
                if(piDeclaredChoices.isSystem() != true) {
                    result.add("Property info for opendma:Choices in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllChoices = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameChoices.equals(pi.getQName())) {
                    if(piAllChoices == null) {
                        piAllChoices = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Choices");
                    }
                }
            }
        }
        if(piAllChoices == null) {
            result.add("All properties in class have no property info object with qname opendma:Choices");
        }
        if(piAllChoices != null) {
            if(!"opendma".equals(piAllChoices.getNamespace())) {
                result.add("Property info for opendma:Choices in all properties qname namespace is not 'opendma'");
            }
            if(!"Choices".equals(piAllChoices.getName())) {
                result.add("Property info for opendma:Choices in all properties qname name is not 'Choices'");
            }
            if(piAllChoices.getDataType() != 10) {
                result.add("Property info for opendma:Choices in all properties data type is not '10'");
            }
            if(piAllChoices.isMultiValue() != true) {
                result.add("Property info for opendma:Choices in all properties MultiValue is not 'true'");
            }
            if(piAllChoices.isReadOnly() != false) {
                result.add("Property info for opendma:Choices in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","ChoiceValue")).equals(piAllChoices.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Choices in all properties ReadOnly is not 'false'");
            }
            if(piAllChoices.isHidden() != false) {
                result.add("Property info for opendma:Choices in all properties Hidden is not 'false'");
            }
            if(piAllChoices.isRequired() != false) {
                result.add("Property info for opendma:Choices in all properties Required is not 'false'");
            }
            if(piAllChoices.isSystem() != true) {
                result.add("Property info for opendma:Choices in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaChoiceValue(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaChoiceValue)) {
            result.add("Does not implement OdmaChoiceValue interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:DisplayName
        OdmaQName qnameDisplayName = new OdmaQName("opendma","DisplayName");
        try {
            OdmaProperty propDisplayName = obj.getProperty(qnameDisplayName);
            if(propDisplayName.getName() == null) {
                result.add("Property opendma:DisplayName qname is null");
            }
            if(!"opendma".equals(propDisplayName.getName().getNamespace())) {
                result.add("Property opendma:DisplayName qname namespace is not 'opendma', found instead'"+propDisplayName.getName().getNamespace()+"'");
            }
            if(!"DisplayName".equals(propDisplayName.getName().getName())) {
                result.add("Property opendma:DisplayName qname name is not 'DisplayName', found instead'"+propDisplayName.getName().getName()+"'");
            }
            if(propDisplayName.getType() != OdmaType.STRING) {
                result.add("Property opendma:DisplayName type is not 'STRING'");
            }
            if(propDisplayName.isMultiValue() != false) {
                result.add("Property opendma:DisplayName MultiValue is not 'false'");
            }
            if(propDisplayName.getValue() == null) {
                result.add("Property opendma:DisplayName is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DisplayName");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDisplayName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDisplayName.equals(pi.getQName())) {
                        if(piDeclaredDisplayName == null) {
                            piDeclaredDisplayName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DisplayName");
                        }
                    }
                }
            }
            if(piDeclaredDisplayName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DisplayName");
            }
            if(piDeclaredDisplayName != null) {
                if(!"opendma".equals(piDeclaredDisplayName.getNamespace())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname namespace is not 'opendma'");
                }
                if(!"DisplayName".equals(piDeclaredDisplayName.getName())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname name is not 'DisplayName'");
                }
                if(piDeclaredDisplayName.getDataType() != 1) {
                    result.add("Property info for opendma:DisplayName in declared properties data type is not '1'");
                }
                if(piDeclaredDisplayName.isMultiValue() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredDisplayName.isReadOnly() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDisplayName.isHidden() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in declared properties Required is not 'true'");
                }
                if(piDeclaredDisplayName.isSystem() != true) {
                    result.add("Property info for opendma:DisplayName in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDisplayName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDisplayName.equals(pi.getQName())) {
                    if(piAllDisplayName == null) {
                        piAllDisplayName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DisplayName");
                    }
                }
            }
        }
        if(piAllDisplayName == null) {
            result.add("All properties in class have no property info object with qname opendma:DisplayName");
        }
        if(piAllDisplayName != null) {
            if(!"opendma".equals(piAllDisplayName.getNamespace())) {
                result.add("Property info for opendma:DisplayName in all properties qname namespace is not 'opendma'");
            }
            if(!"DisplayName".equals(piAllDisplayName.getName())) {
                result.add("Property info for opendma:DisplayName in all properties qname name is not 'DisplayName'");
            }
            if(piAllDisplayName.getDataType() != 1) {
                result.add("Property info for opendma:DisplayName in all properties data type is not '1'");
            }
            if(piAllDisplayName.isMultiValue() != false) {
                result.add("Property info for opendma:DisplayName in all properties MultiValue is not 'false'");
            }
            if(piAllDisplayName.isReadOnly() != false) {
                result.add("Property info for opendma:DisplayName in all properties ReadOnly is not 'false'");
            }
            if(piAllDisplayName.isHidden() != false) {
                result.add("Property info for opendma:DisplayName in all properties Hidden is not 'false'");
            }
            if(piAllDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in all properties Required is not 'true'");
            }
            if(piAllDisplayName.isSystem() != true) {
                result.add("Property info for opendma:DisplayName in all properties System is not 'true'");
            }
        }
        // opendma:StringValue
        OdmaQName qnameStringValue = new OdmaQName("opendma","StringValue");
        try {
            OdmaProperty propStringValue = obj.getProperty(qnameStringValue);
            if(propStringValue.getName() == null) {
                result.add("Property opendma:StringValue qname is null");
            }
            if(!"opendma".equals(propStringValue.getName().getNamespace())) {
                result.add("Property opendma:StringValue qname namespace is not 'opendma', found instead'"+propStringValue.getName().getNamespace()+"'");
            }
            if(!"StringValue".equals(propStringValue.getName().getName())) {
                result.add("Property opendma:StringValue qname name is not 'StringValue', found instead'"+propStringValue.getName().getName()+"'");
            }
            if(propStringValue.getType() != OdmaType.STRING) {
                result.add("Property opendma:StringValue type is not 'STRING'");
            }
            if(propStringValue.isMultiValue() != false) {
                result.add("Property opendma:StringValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:StringValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredStringValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameStringValue.equals(pi.getQName())) {
                        if(piDeclaredStringValue == null) {
                            piDeclaredStringValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:StringValue");
                        }
                    }
                }
            }
            if(piDeclaredStringValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:StringValue");
            }
            if(piDeclaredStringValue != null) {
                if(!"opendma".equals(piDeclaredStringValue.getNamespace())) {
                    result.add("Property info for opendma:StringValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"StringValue".equals(piDeclaredStringValue.getName())) {
                    result.add("Property info for opendma:StringValue in declared properties qname name is not 'StringValue'");
                }
                if(piDeclaredStringValue.getDataType() != 1) {
                    result.add("Property info for opendma:StringValue in declared properties data type is not '1'");
                }
                if(piDeclaredStringValue.isMultiValue() != false) {
                    result.add("Property info for opendma:StringValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredStringValue.isReadOnly() != false) {
                    result.add("Property info for opendma:StringValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredStringValue.isHidden() != false) {
                    result.add("Property info for opendma:StringValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredStringValue.isRequired() != false) {
                result.add("Property info for opendma:StringValue in declared properties Required is not 'false'");
                }
                if(piDeclaredStringValue.isSystem() != true) {
                    result.add("Property info for opendma:StringValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllStringValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameStringValue.equals(pi.getQName())) {
                    if(piAllStringValue == null) {
                        piAllStringValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:StringValue");
                    }
                }
            }
        }
        if(piAllStringValue == null) {
            result.add("All properties in class have no property info object with qname opendma:StringValue");
        }
        if(piAllStringValue != null) {
            if(!"opendma".equals(piAllStringValue.getNamespace())) {
                result.add("Property info for opendma:StringValue in all properties qname namespace is not 'opendma'");
            }
            if(!"StringValue".equals(piAllStringValue.getName())) {
                result.add("Property info for opendma:StringValue in all properties qname name is not 'StringValue'");
            }
            if(piAllStringValue.getDataType() != 1) {
                result.add("Property info for opendma:StringValue in all properties data type is not '1'");
            }
            if(piAllStringValue.isMultiValue() != false) {
                result.add("Property info for opendma:StringValue in all properties MultiValue is not 'false'");
            }
            if(piAllStringValue.isReadOnly() != false) {
                result.add("Property info for opendma:StringValue in all properties ReadOnly is not 'false'");
            }
            if(piAllStringValue.isHidden() != false) {
                result.add("Property info for opendma:StringValue in all properties Hidden is not 'false'");
            }
            if(piAllStringValue.isRequired() != false) {
                result.add("Property info for opendma:StringValue in all properties Required is not 'false'");
            }
            if(piAllStringValue.isSystem() != true) {
                result.add("Property info for opendma:StringValue in all properties System is not 'true'");
            }
        }
        // opendma:IntegerValue
        OdmaQName qnameIntegerValue = new OdmaQName("opendma","IntegerValue");
        try {
            OdmaProperty propIntegerValue = obj.getProperty(qnameIntegerValue);
            if(propIntegerValue.getName() == null) {
                result.add("Property opendma:IntegerValue qname is null");
            }
            if(!"opendma".equals(propIntegerValue.getName().getNamespace())) {
                result.add("Property opendma:IntegerValue qname namespace is not 'opendma', found instead'"+propIntegerValue.getName().getNamespace()+"'");
            }
            if(!"IntegerValue".equals(propIntegerValue.getName().getName())) {
                result.add("Property opendma:IntegerValue qname name is not 'IntegerValue', found instead'"+propIntegerValue.getName().getName()+"'");
            }
            if(propIntegerValue.getType() != OdmaType.INTEGER) {
                result.add("Property opendma:IntegerValue type is not 'INTEGER'");
            }
            if(propIntegerValue.isMultiValue() != false) {
                result.add("Property opendma:IntegerValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:IntegerValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredIntegerValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameIntegerValue.equals(pi.getQName())) {
                        if(piDeclaredIntegerValue == null) {
                            piDeclaredIntegerValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:IntegerValue");
                        }
                    }
                }
            }
            if(piDeclaredIntegerValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:IntegerValue");
            }
            if(piDeclaredIntegerValue != null) {
                if(!"opendma".equals(piDeclaredIntegerValue.getNamespace())) {
                    result.add("Property info for opendma:IntegerValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"IntegerValue".equals(piDeclaredIntegerValue.getName())) {
                    result.add("Property info for opendma:IntegerValue in declared properties qname name is not 'IntegerValue'");
                }
                if(piDeclaredIntegerValue.getDataType() != 2) {
                    result.add("Property info for opendma:IntegerValue in declared properties data type is not '2'");
                }
                if(piDeclaredIntegerValue.isMultiValue() != false) {
                    result.add("Property info for opendma:IntegerValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredIntegerValue.isReadOnly() != false) {
                    result.add("Property info for opendma:IntegerValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredIntegerValue.isHidden() != false) {
                    result.add("Property info for opendma:IntegerValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredIntegerValue.isRequired() != false) {
                result.add("Property info for opendma:IntegerValue in declared properties Required is not 'false'");
                }
                if(piDeclaredIntegerValue.isSystem() != true) {
                    result.add("Property info for opendma:IntegerValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllIntegerValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameIntegerValue.equals(pi.getQName())) {
                    if(piAllIntegerValue == null) {
                        piAllIntegerValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:IntegerValue");
                    }
                }
            }
        }
        if(piAllIntegerValue == null) {
            result.add("All properties in class have no property info object with qname opendma:IntegerValue");
        }
        if(piAllIntegerValue != null) {
            if(!"opendma".equals(piAllIntegerValue.getNamespace())) {
                result.add("Property info for opendma:IntegerValue in all properties qname namespace is not 'opendma'");
            }
            if(!"IntegerValue".equals(piAllIntegerValue.getName())) {
                result.add("Property info for opendma:IntegerValue in all properties qname name is not 'IntegerValue'");
            }
            if(piAllIntegerValue.getDataType() != 2) {
                result.add("Property info for opendma:IntegerValue in all properties data type is not '2'");
            }
            if(piAllIntegerValue.isMultiValue() != false) {
                result.add("Property info for opendma:IntegerValue in all properties MultiValue is not 'false'");
            }
            if(piAllIntegerValue.isReadOnly() != false) {
                result.add("Property info for opendma:IntegerValue in all properties ReadOnly is not 'false'");
            }
            if(piAllIntegerValue.isHidden() != false) {
                result.add("Property info for opendma:IntegerValue in all properties Hidden is not 'false'");
            }
            if(piAllIntegerValue.isRequired() != false) {
                result.add("Property info for opendma:IntegerValue in all properties Required is not 'false'");
            }
            if(piAllIntegerValue.isSystem() != true) {
                result.add("Property info for opendma:IntegerValue in all properties System is not 'true'");
            }
        }
        // opendma:ShortValue
        OdmaQName qnameShortValue = new OdmaQName("opendma","ShortValue");
        try {
            OdmaProperty propShortValue = obj.getProperty(qnameShortValue);
            if(propShortValue.getName() == null) {
                result.add("Property opendma:ShortValue qname is null");
            }
            if(!"opendma".equals(propShortValue.getName().getNamespace())) {
                result.add("Property opendma:ShortValue qname namespace is not 'opendma', found instead'"+propShortValue.getName().getNamespace()+"'");
            }
            if(!"ShortValue".equals(propShortValue.getName().getName())) {
                result.add("Property opendma:ShortValue qname name is not 'ShortValue', found instead'"+propShortValue.getName().getName()+"'");
            }
            if(propShortValue.getType() != OdmaType.SHORT) {
                result.add("Property opendma:ShortValue type is not 'SHORT'");
            }
            if(propShortValue.isMultiValue() != false) {
                result.add("Property opendma:ShortValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ShortValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredShortValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameShortValue.equals(pi.getQName())) {
                        if(piDeclaredShortValue == null) {
                            piDeclaredShortValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ShortValue");
                        }
                    }
                }
            }
            if(piDeclaredShortValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ShortValue");
            }
            if(piDeclaredShortValue != null) {
                if(!"opendma".equals(piDeclaredShortValue.getNamespace())) {
                    result.add("Property info for opendma:ShortValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"ShortValue".equals(piDeclaredShortValue.getName())) {
                    result.add("Property info for opendma:ShortValue in declared properties qname name is not 'ShortValue'");
                }
                if(piDeclaredShortValue.getDataType() != 3) {
                    result.add("Property info for opendma:ShortValue in declared properties data type is not '3'");
                }
                if(piDeclaredShortValue.isMultiValue() != false) {
                    result.add("Property info for opendma:ShortValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredShortValue.isReadOnly() != false) {
                    result.add("Property info for opendma:ShortValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredShortValue.isHidden() != false) {
                    result.add("Property info for opendma:ShortValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredShortValue.isRequired() != false) {
                result.add("Property info for opendma:ShortValue in declared properties Required is not 'false'");
                }
                if(piDeclaredShortValue.isSystem() != true) {
                    result.add("Property info for opendma:ShortValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllShortValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameShortValue.equals(pi.getQName())) {
                    if(piAllShortValue == null) {
                        piAllShortValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ShortValue");
                    }
                }
            }
        }
        if(piAllShortValue == null) {
            result.add("All properties in class have no property info object with qname opendma:ShortValue");
        }
        if(piAllShortValue != null) {
            if(!"opendma".equals(piAllShortValue.getNamespace())) {
                result.add("Property info for opendma:ShortValue in all properties qname namespace is not 'opendma'");
            }
            if(!"ShortValue".equals(piAllShortValue.getName())) {
                result.add("Property info for opendma:ShortValue in all properties qname name is not 'ShortValue'");
            }
            if(piAllShortValue.getDataType() != 3) {
                result.add("Property info for opendma:ShortValue in all properties data type is not '3'");
            }
            if(piAllShortValue.isMultiValue() != false) {
                result.add("Property info for opendma:ShortValue in all properties MultiValue is not 'false'");
            }
            if(piAllShortValue.isReadOnly() != false) {
                result.add("Property info for opendma:ShortValue in all properties ReadOnly is not 'false'");
            }
            if(piAllShortValue.isHidden() != false) {
                result.add("Property info for opendma:ShortValue in all properties Hidden is not 'false'");
            }
            if(piAllShortValue.isRequired() != false) {
                result.add("Property info for opendma:ShortValue in all properties Required is not 'false'");
            }
            if(piAllShortValue.isSystem() != true) {
                result.add("Property info for opendma:ShortValue in all properties System is not 'true'");
            }
        }
        // opendma:LongValue
        OdmaQName qnameLongValue = new OdmaQName("opendma","LongValue");
        try {
            OdmaProperty propLongValue = obj.getProperty(qnameLongValue);
            if(propLongValue.getName() == null) {
                result.add("Property opendma:LongValue qname is null");
            }
            if(!"opendma".equals(propLongValue.getName().getNamespace())) {
                result.add("Property opendma:LongValue qname namespace is not 'opendma', found instead'"+propLongValue.getName().getNamespace()+"'");
            }
            if(!"LongValue".equals(propLongValue.getName().getName())) {
                result.add("Property opendma:LongValue qname name is not 'LongValue', found instead'"+propLongValue.getName().getName()+"'");
            }
            if(propLongValue.getType() != OdmaType.LONG) {
                result.add("Property opendma:LongValue type is not 'LONG'");
            }
            if(propLongValue.isMultiValue() != false) {
                result.add("Property opendma:LongValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:LongValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLongValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLongValue.equals(pi.getQName())) {
                        if(piDeclaredLongValue == null) {
                            piDeclaredLongValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:LongValue");
                        }
                    }
                }
            }
            if(piDeclaredLongValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:LongValue");
            }
            if(piDeclaredLongValue != null) {
                if(!"opendma".equals(piDeclaredLongValue.getNamespace())) {
                    result.add("Property info for opendma:LongValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"LongValue".equals(piDeclaredLongValue.getName())) {
                    result.add("Property info for opendma:LongValue in declared properties qname name is not 'LongValue'");
                }
                if(piDeclaredLongValue.getDataType() != 4) {
                    result.add("Property info for opendma:LongValue in declared properties data type is not '4'");
                }
                if(piDeclaredLongValue.isMultiValue() != false) {
                    result.add("Property info for opendma:LongValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLongValue.isReadOnly() != false) {
                    result.add("Property info for opendma:LongValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredLongValue.isHidden() != false) {
                    result.add("Property info for opendma:LongValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLongValue.isRequired() != false) {
                result.add("Property info for opendma:LongValue in declared properties Required is not 'false'");
                }
                if(piDeclaredLongValue.isSystem() != true) {
                    result.add("Property info for opendma:LongValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLongValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLongValue.equals(pi.getQName())) {
                    if(piAllLongValue == null) {
                        piAllLongValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:LongValue");
                    }
                }
            }
        }
        if(piAllLongValue == null) {
            result.add("All properties in class have no property info object with qname opendma:LongValue");
        }
        if(piAllLongValue != null) {
            if(!"opendma".equals(piAllLongValue.getNamespace())) {
                result.add("Property info for opendma:LongValue in all properties qname namespace is not 'opendma'");
            }
            if(!"LongValue".equals(piAllLongValue.getName())) {
                result.add("Property info for opendma:LongValue in all properties qname name is not 'LongValue'");
            }
            if(piAllLongValue.getDataType() != 4) {
                result.add("Property info for opendma:LongValue in all properties data type is not '4'");
            }
            if(piAllLongValue.isMultiValue() != false) {
                result.add("Property info for opendma:LongValue in all properties MultiValue is not 'false'");
            }
            if(piAllLongValue.isReadOnly() != false) {
                result.add("Property info for opendma:LongValue in all properties ReadOnly is not 'false'");
            }
            if(piAllLongValue.isHidden() != false) {
                result.add("Property info for opendma:LongValue in all properties Hidden is not 'false'");
            }
            if(piAllLongValue.isRequired() != false) {
                result.add("Property info for opendma:LongValue in all properties Required is not 'false'");
            }
            if(piAllLongValue.isSystem() != true) {
                result.add("Property info for opendma:LongValue in all properties System is not 'true'");
            }
        }
        // opendma:FloatValue
        OdmaQName qnameFloatValue = new OdmaQName("opendma","FloatValue");
        try {
            OdmaProperty propFloatValue = obj.getProperty(qnameFloatValue);
            if(propFloatValue.getName() == null) {
                result.add("Property opendma:FloatValue qname is null");
            }
            if(!"opendma".equals(propFloatValue.getName().getNamespace())) {
                result.add("Property opendma:FloatValue qname namespace is not 'opendma', found instead'"+propFloatValue.getName().getNamespace()+"'");
            }
            if(!"FloatValue".equals(propFloatValue.getName().getName())) {
                result.add("Property opendma:FloatValue qname name is not 'FloatValue', found instead'"+propFloatValue.getName().getName()+"'");
            }
            if(propFloatValue.getType() != OdmaType.FLOAT) {
                result.add("Property opendma:FloatValue type is not 'FLOAT'");
            }
            if(propFloatValue.isMultiValue() != false) {
                result.add("Property opendma:FloatValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:FloatValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredFloatValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameFloatValue.equals(pi.getQName())) {
                        if(piDeclaredFloatValue == null) {
                            piDeclaredFloatValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:FloatValue");
                        }
                    }
                }
            }
            if(piDeclaredFloatValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:FloatValue");
            }
            if(piDeclaredFloatValue != null) {
                if(!"opendma".equals(piDeclaredFloatValue.getNamespace())) {
                    result.add("Property info for opendma:FloatValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"FloatValue".equals(piDeclaredFloatValue.getName())) {
                    result.add("Property info for opendma:FloatValue in declared properties qname name is not 'FloatValue'");
                }
                if(piDeclaredFloatValue.getDataType() != 5) {
                    result.add("Property info for opendma:FloatValue in declared properties data type is not '5'");
                }
                if(piDeclaredFloatValue.isMultiValue() != false) {
                    result.add("Property info for opendma:FloatValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredFloatValue.isReadOnly() != false) {
                    result.add("Property info for opendma:FloatValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredFloatValue.isHidden() != false) {
                    result.add("Property info for opendma:FloatValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredFloatValue.isRequired() != false) {
                result.add("Property info for opendma:FloatValue in declared properties Required is not 'false'");
                }
                if(piDeclaredFloatValue.isSystem() != true) {
                    result.add("Property info for opendma:FloatValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllFloatValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameFloatValue.equals(pi.getQName())) {
                    if(piAllFloatValue == null) {
                        piAllFloatValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:FloatValue");
                    }
                }
            }
        }
        if(piAllFloatValue == null) {
            result.add("All properties in class have no property info object with qname opendma:FloatValue");
        }
        if(piAllFloatValue != null) {
            if(!"opendma".equals(piAllFloatValue.getNamespace())) {
                result.add("Property info for opendma:FloatValue in all properties qname namespace is not 'opendma'");
            }
            if(!"FloatValue".equals(piAllFloatValue.getName())) {
                result.add("Property info for opendma:FloatValue in all properties qname name is not 'FloatValue'");
            }
            if(piAllFloatValue.getDataType() != 5) {
                result.add("Property info for opendma:FloatValue in all properties data type is not '5'");
            }
            if(piAllFloatValue.isMultiValue() != false) {
                result.add("Property info for opendma:FloatValue in all properties MultiValue is not 'false'");
            }
            if(piAllFloatValue.isReadOnly() != false) {
                result.add("Property info for opendma:FloatValue in all properties ReadOnly is not 'false'");
            }
            if(piAllFloatValue.isHidden() != false) {
                result.add("Property info for opendma:FloatValue in all properties Hidden is not 'false'");
            }
            if(piAllFloatValue.isRequired() != false) {
                result.add("Property info for opendma:FloatValue in all properties Required is not 'false'");
            }
            if(piAllFloatValue.isSystem() != true) {
                result.add("Property info for opendma:FloatValue in all properties System is not 'true'");
            }
        }
        // opendma:DoubleValue
        OdmaQName qnameDoubleValue = new OdmaQName("opendma","DoubleValue");
        try {
            OdmaProperty propDoubleValue = obj.getProperty(qnameDoubleValue);
            if(propDoubleValue.getName() == null) {
                result.add("Property opendma:DoubleValue qname is null");
            }
            if(!"opendma".equals(propDoubleValue.getName().getNamespace())) {
                result.add("Property opendma:DoubleValue qname namespace is not 'opendma', found instead'"+propDoubleValue.getName().getNamespace()+"'");
            }
            if(!"DoubleValue".equals(propDoubleValue.getName().getName())) {
                result.add("Property opendma:DoubleValue qname name is not 'DoubleValue', found instead'"+propDoubleValue.getName().getName()+"'");
            }
            if(propDoubleValue.getType() != OdmaType.DOUBLE) {
                result.add("Property opendma:DoubleValue type is not 'DOUBLE'");
            }
            if(propDoubleValue.isMultiValue() != false) {
                result.add("Property opendma:DoubleValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DoubleValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDoubleValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDoubleValue.equals(pi.getQName())) {
                        if(piDeclaredDoubleValue == null) {
                            piDeclaredDoubleValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DoubleValue");
                        }
                    }
                }
            }
            if(piDeclaredDoubleValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DoubleValue");
            }
            if(piDeclaredDoubleValue != null) {
                if(!"opendma".equals(piDeclaredDoubleValue.getNamespace())) {
                    result.add("Property info for opendma:DoubleValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"DoubleValue".equals(piDeclaredDoubleValue.getName())) {
                    result.add("Property info for opendma:DoubleValue in declared properties qname name is not 'DoubleValue'");
                }
                if(piDeclaredDoubleValue.getDataType() != 6) {
                    result.add("Property info for opendma:DoubleValue in declared properties data type is not '6'");
                }
                if(piDeclaredDoubleValue.isMultiValue() != false) {
                    result.add("Property info for opendma:DoubleValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredDoubleValue.isReadOnly() != false) {
                    result.add("Property info for opendma:DoubleValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDoubleValue.isHidden() != false) {
                    result.add("Property info for opendma:DoubleValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDoubleValue.isRequired() != false) {
                result.add("Property info for opendma:DoubleValue in declared properties Required is not 'false'");
                }
                if(piDeclaredDoubleValue.isSystem() != true) {
                    result.add("Property info for opendma:DoubleValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDoubleValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDoubleValue.equals(pi.getQName())) {
                    if(piAllDoubleValue == null) {
                        piAllDoubleValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DoubleValue");
                    }
                }
            }
        }
        if(piAllDoubleValue == null) {
            result.add("All properties in class have no property info object with qname opendma:DoubleValue");
        }
        if(piAllDoubleValue != null) {
            if(!"opendma".equals(piAllDoubleValue.getNamespace())) {
                result.add("Property info for opendma:DoubleValue in all properties qname namespace is not 'opendma'");
            }
            if(!"DoubleValue".equals(piAllDoubleValue.getName())) {
                result.add("Property info for opendma:DoubleValue in all properties qname name is not 'DoubleValue'");
            }
            if(piAllDoubleValue.getDataType() != 6) {
                result.add("Property info for opendma:DoubleValue in all properties data type is not '6'");
            }
            if(piAllDoubleValue.isMultiValue() != false) {
                result.add("Property info for opendma:DoubleValue in all properties MultiValue is not 'false'");
            }
            if(piAllDoubleValue.isReadOnly() != false) {
                result.add("Property info for opendma:DoubleValue in all properties ReadOnly is not 'false'");
            }
            if(piAllDoubleValue.isHidden() != false) {
                result.add("Property info for opendma:DoubleValue in all properties Hidden is not 'false'");
            }
            if(piAllDoubleValue.isRequired() != false) {
                result.add("Property info for opendma:DoubleValue in all properties Required is not 'false'");
            }
            if(piAllDoubleValue.isSystem() != true) {
                result.add("Property info for opendma:DoubleValue in all properties System is not 'true'");
            }
        }
        // opendma:BooleanValue
        OdmaQName qnameBooleanValue = new OdmaQName("opendma","BooleanValue");
        try {
            OdmaProperty propBooleanValue = obj.getProperty(qnameBooleanValue);
            if(propBooleanValue.getName() == null) {
                result.add("Property opendma:BooleanValue qname is null");
            }
            if(!"opendma".equals(propBooleanValue.getName().getNamespace())) {
                result.add("Property opendma:BooleanValue qname namespace is not 'opendma', found instead'"+propBooleanValue.getName().getNamespace()+"'");
            }
            if(!"BooleanValue".equals(propBooleanValue.getName().getName())) {
                result.add("Property opendma:BooleanValue qname name is not 'BooleanValue', found instead'"+propBooleanValue.getName().getName()+"'");
            }
            if(propBooleanValue.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:BooleanValue type is not 'BOOLEAN'");
            }
            if(propBooleanValue.isMultiValue() != false) {
                result.add("Property opendma:BooleanValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:BooleanValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredBooleanValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameBooleanValue.equals(pi.getQName())) {
                        if(piDeclaredBooleanValue == null) {
                            piDeclaredBooleanValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:BooleanValue");
                        }
                    }
                }
            }
            if(piDeclaredBooleanValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:BooleanValue");
            }
            if(piDeclaredBooleanValue != null) {
                if(!"opendma".equals(piDeclaredBooleanValue.getNamespace())) {
                    result.add("Property info for opendma:BooleanValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"BooleanValue".equals(piDeclaredBooleanValue.getName())) {
                    result.add("Property info for opendma:BooleanValue in declared properties qname name is not 'BooleanValue'");
                }
                if(piDeclaredBooleanValue.getDataType() != 7) {
                    result.add("Property info for opendma:BooleanValue in declared properties data type is not '7'");
                }
                if(piDeclaredBooleanValue.isMultiValue() != false) {
                    result.add("Property info for opendma:BooleanValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredBooleanValue.isReadOnly() != false) {
                    result.add("Property info for opendma:BooleanValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredBooleanValue.isHidden() != false) {
                    result.add("Property info for opendma:BooleanValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredBooleanValue.isRequired() != false) {
                result.add("Property info for opendma:BooleanValue in declared properties Required is not 'false'");
                }
                if(piDeclaredBooleanValue.isSystem() != true) {
                    result.add("Property info for opendma:BooleanValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllBooleanValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameBooleanValue.equals(pi.getQName())) {
                    if(piAllBooleanValue == null) {
                        piAllBooleanValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:BooleanValue");
                    }
                }
            }
        }
        if(piAllBooleanValue == null) {
            result.add("All properties in class have no property info object with qname opendma:BooleanValue");
        }
        if(piAllBooleanValue != null) {
            if(!"opendma".equals(piAllBooleanValue.getNamespace())) {
                result.add("Property info for opendma:BooleanValue in all properties qname namespace is not 'opendma'");
            }
            if(!"BooleanValue".equals(piAllBooleanValue.getName())) {
                result.add("Property info for opendma:BooleanValue in all properties qname name is not 'BooleanValue'");
            }
            if(piAllBooleanValue.getDataType() != 7) {
                result.add("Property info for opendma:BooleanValue in all properties data type is not '7'");
            }
            if(piAllBooleanValue.isMultiValue() != false) {
                result.add("Property info for opendma:BooleanValue in all properties MultiValue is not 'false'");
            }
            if(piAllBooleanValue.isReadOnly() != false) {
                result.add("Property info for opendma:BooleanValue in all properties ReadOnly is not 'false'");
            }
            if(piAllBooleanValue.isHidden() != false) {
                result.add("Property info for opendma:BooleanValue in all properties Hidden is not 'false'");
            }
            if(piAllBooleanValue.isRequired() != false) {
                result.add("Property info for opendma:BooleanValue in all properties Required is not 'false'");
            }
            if(piAllBooleanValue.isSystem() != true) {
                result.add("Property info for opendma:BooleanValue in all properties System is not 'true'");
            }
        }
        // opendma:DateTimeValue
        OdmaQName qnameDateTimeValue = new OdmaQName("opendma","DateTimeValue");
        try {
            OdmaProperty propDateTimeValue = obj.getProperty(qnameDateTimeValue);
            if(propDateTimeValue.getName() == null) {
                result.add("Property opendma:DateTimeValue qname is null");
            }
            if(!"opendma".equals(propDateTimeValue.getName().getNamespace())) {
                result.add("Property opendma:DateTimeValue qname namespace is not 'opendma', found instead'"+propDateTimeValue.getName().getNamespace()+"'");
            }
            if(!"DateTimeValue".equals(propDateTimeValue.getName().getName())) {
                result.add("Property opendma:DateTimeValue qname name is not 'DateTimeValue', found instead'"+propDateTimeValue.getName().getName()+"'");
            }
            if(propDateTimeValue.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:DateTimeValue type is not 'DATETIME'");
            }
            if(propDateTimeValue.isMultiValue() != false) {
                result.add("Property opendma:DateTimeValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DateTimeValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDateTimeValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDateTimeValue.equals(pi.getQName())) {
                        if(piDeclaredDateTimeValue == null) {
                            piDeclaredDateTimeValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DateTimeValue");
                        }
                    }
                }
            }
            if(piDeclaredDateTimeValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DateTimeValue");
            }
            if(piDeclaredDateTimeValue != null) {
                if(!"opendma".equals(piDeclaredDateTimeValue.getNamespace())) {
                    result.add("Property info for opendma:DateTimeValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"DateTimeValue".equals(piDeclaredDateTimeValue.getName())) {
                    result.add("Property info for opendma:DateTimeValue in declared properties qname name is not 'DateTimeValue'");
                }
                if(piDeclaredDateTimeValue.getDataType() != 8) {
                    result.add("Property info for opendma:DateTimeValue in declared properties data type is not '8'");
                }
                if(piDeclaredDateTimeValue.isMultiValue() != false) {
                    result.add("Property info for opendma:DateTimeValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredDateTimeValue.isReadOnly() != false) {
                    result.add("Property info for opendma:DateTimeValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDateTimeValue.isHidden() != false) {
                    result.add("Property info for opendma:DateTimeValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDateTimeValue.isRequired() != false) {
                result.add("Property info for opendma:DateTimeValue in declared properties Required is not 'false'");
                }
                if(piDeclaredDateTimeValue.isSystem() != true) {
                    result.add("Property info for opendma:DateTimeValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDateTimeValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDateTimeValue.equals(pi.getQName())) {
                    if(piAllDateTimeValue == null) {
                        piAllDateTimeValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DateTimeValue");
                    }
                }
            }
        }
        if(piAllDateTimeValue == null) {
            result.add("All properties in class have no property info object with qname opendma:DateTimeValue");
        }
        if(piAllDateTimeValue != null) {
            if(!"opendma".equals(piAllDateTimeValue.getNamespace())) {
                result.add("Property info for opendma:DateTimeValue in all properties qname namespace is not 'opendma'");
            }
            if(!"DateTimeValue".equals(piAllDateTimeValue.getName())) {
                result.add("Property info for opendma:DateTimeValue in all properties qname name is not 'DateTimeValue'");
            }
            if(piAllDateTimeValue.getDataType() != 8) {
                result.add("Property info for opendma:DateTimeValue in all properties data type is not '8'");
            }
            if(piAllDateTimeValue.isMultiValue() != false) {
                result.add("Property info for opendma:DateTimeValue in all properties MultiValue is not 'false'");
            }
            if(piAllDateTimeValue.isReadOnly() != false) {
                result.add("Property info for opendma:DateTimeValue in all properties ReadOnly is not 'false'");
            }
            if(piAllDateTimeValue.isHidden() != false) {
                result.add("Property info for opendma:DateTimeValue in all properties Hidden is not 'false'");
            }
            if(piAllDateTimeValue.isRequired() != false) {
                result.add("Property info for opendma:DateTimeValue in all properties Required is not 'false'");
            }
            if(piAllDateTimeValue.isSystem() != true) {
                result.add("Property info for opendma:DateTimeValue in all properties System is not 'true'");
            }
        }
        // opendma:BlobValue
        OdmaQName qnameBlobValue = new OdmaQName("opendma","BlobValue");
        try {
            OdmaProperty propBlobValue = obj.getProperty(qnameBlobValue);
            if(propBlobValue.getName() == null) {
                result.add("Property opendma:BlobValue qname is null");
            }
            if(!"opendma".equals(propBlobValue.getName().getNamespace())) {
                result.add("Property opendma:BlobValue qname namespace is not 'opendma', found instead'"+propBlobValue.getName().getNamespace()+"'");
            }
            if(!"BlobValue".equals(propBlobValue.getName().getName())) {
                result.add("Property opendma:BlobValue qname name is not 'BlobValue', found instead'"+propBlobValue.getName().getName()+"'");
            }
            if(propBlobValue.getType() != OdmaType.BLOB) {
                result.add("Property opendma:BlobValue type is not 'BLOB'");
            }
            if(propBlobValue.isMultiValue() != false) {
                result.add("Property opendma:BlobValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:BlobValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredBlobValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameBlobValue.equals(pi.getQName())) {
                        if(piDeclaredBlobValue == null) {
                            piDeclaredBlobValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:BlobValue");
                        }
                    }
                }
            }
            if(piDeclaredBlobValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:BlobValue");
            }
            if(piDeclaredBlobValue != null) {
                if(!"opendma".equals(piDeclaredBlobValue.getNamespace())) {
                    result.add("Property info for opendma:BlobValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"BlobValue".equals(piDeclaredBlobValue.getName())) {
                    result.add("Property info for opendma:BlobValue in declared properties qname name is not 'BlobValue'");
                }
                if(piDeclaredBlobValue.getDataType() != 9) {
                    result.add("Property info for opendma:BlobValue in declared properties data type is not '9'");
                }
                if(piDeclaredBlobValue.isMultiValue() != false) {
                    result.add("Property info for opendma:BlobValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredBlobValue.isReadOnly() != false) {
                    result.add("Property info for opendma:BlobValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredBlobValue.isHidden() != false) {
                    result.add("Property info for opendma:BlobValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredBlobValue.isRequired() != false) {
                result.add("Property info for opendma:BlobValue in declared properties Required is not 'false'");
                }
                if(piDeclaredBlobValue.isSystem() != true) {
                    result.add("Property info for opendma:BlobValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllBlobValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameBlobValue.equals(pi.getQName())) {
                    if(piAllBlobValue == null) {
                        piAllBlobValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:BlobValue");
                    }
                }
            }
        }
        if(piAllBlobValue == null) {
            result.add("All properties in class have no property info object with qname opendma:BlobValue");
        }
        if(piAllBlobValue != null) {
            if(!"opendma".equals(piAllBlobValue.getNamespace())) {
                result.add("Property info for opendma:BlobValue in all properties qname namespace is not 'opendma'");
            }
            if(!"BlobValue".equals(piAllBlobValue.getName())) {
                result.add("Property info for opendma:BlobValue in all properties qname name is not 'BlobValue'");
            }
            if(piAllBlobValue.getDataType() != 9) {
                result.add("Property info for opendma:BlobValue in all properties data type is not '9'");
            }
            if(piAllBlobValue.isMultiValue() != false) {
                result.add("Property info for opendma:BlobValue in all properties MultiValue is not 'false'");
            }
            if(piAllBlobValue.isReadOnly() != false) {
                result.add("Property info for opendma:BlobValue in all properties ReadOnly is not 'false'");
            }
            if(piAllBlobValue.isHidden() != false) {
                result.add("Property info for opendma:BlobValue in all properties Hidden is not 'false'");
            }
            if(piAllBlobValue.isRequired() != false) {
                result.add("Property info for opendma:BlobValue in all properties Required is not 'false'");
            }
            if(piAllBlobValue.isSystem() != true) {
                result.add("Property info for opendma:BlobValue in all properties System is not 'true'");
            }
        }
        // opendma:ReferenceValue
        OdmaQName qnameReferenceValue = new OdmaQName("opendma","ReferenceValue");
        try {
            OdmaProperty propReferenceValue = obj.getProperty(qnameReferenceValue);
            if(propReferenceValue.getName() == null) {
                result.add("Property opendma:ReferenceValue qname is null");
            }
            if(!"opendma".equals(propReferenceValue.getName().getNamespace())) {
                result.add("Property opendma:ReferenceValue qname namespace is not 'opendma', found instead'"+propReferenceValue.getName().getNamespace()+"'");
            }
            if(!"ReferenceValue".equals(propReferenceValue.getName().getName())) {
                result.add("Property opendma:ReferenceValue qname name is not 'ReferenceValue', found instead'"+propReferenceValue.getName().getName()+"'");
            }
            if(propReferenceValue.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:ReferenceValue type is not 'REFERENCE'");
            }
            if(propReferenceValue.isMultiValue() != false) {
                result.add("Property opendma:ReferenceValue MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ReferenceValue");
        }
        if(clazz != null && (new OdmaQName("opendma","ChoiceValue")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredReferenceValue = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameReferenceValue.equals(pi.getQName())) {
                        if(piDeclaredReferenceValue == null) {
                            piDeclaredReferenceValue = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ReferenceValue");
                        }
                    }
                }
            }
            if(piDeclaredReferenceValue == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ReferenceValue");
            }
            if(piDeclaredReferenceValue != null) {
                if(!"opendma".equals(piDeclaredReferenceValue.getNamespace())) {
                    result.add("Property info for opendma:ReferenceValue in declared properties qname namespace is not 'opendma'");
                }
                if(!"ReferenceValue".equals(piDeclaredReferenceValue.getName())) {
                    result.add("Property info for opendma:ReferenceValue in declared properties qname name is not 'ReferenceValue'");
                }
                if(piDeclaredReferenceValue.getDataType() != 10) {
                    result.add("Property info for opendma:ReferenceValue in declared properties data type is not '10'");
                }
                if(piDeclaredReferenceValue.isMultiValue() != false) {
                    result.add("Property info for opendma:ReferenceValue in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredReferenceValue.isReadOnly() != false) {
                    result.add("Property info for opendma:ReferenceValue in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","Object")).equals(piDeclaredReferenceValue.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:ReferenceValue in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredReferenceValue.isHidden() != false) {
                    result.add("Property info for opendma:ReferenceValue in declared properties Hidden is not 'false'");
                }
                if(piDeclaredReferenceValue.isRequired() != false) {
                result.add("Property info for opendma:ReferenceValue in declared properties Required is not 'false'");
                }
                if(piDeclaredReferenceValue.isSystem() != true) {
                    result.add("Property info for opendma:ReferenceValue in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllReferenceValue = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameReferenceValue.equals(pi.getQName())) {
                    if(piAllReferenceValue == null) {
                        piAllReferenceValue = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ReferenceValue");
                    }
                }
            }
        }
        if(piAllReferenceValue == null) {
            result.add("All properties in class have no property info object with qname opendma:ReferenceValue");
        }
        if(piAllReferenceValue != null) {
            if(!"opendma".equals(piAllReferenceValue.getNamespace())) {
                result.add("Property info for opendma:ReferenceValue in all properties qname namespace is not 'opendma'");
            }
            if(!"ReferenceValue".equals(piAllReferenceValue.getName())) {
                result.add("Property info for opendma:ReferenceValue in all properties qname name is not 'ReferenceValue'");
            }
            if(piAllReferenceValue.getDataType() != 10) {
                result.add("Property info for opendma:ReferenceValue in all properties data type is not '10'");
            }
            if(piAllReferenceValue.isMultiValue() != false) {
                result.add("Property info for opendma:ReferenceValue in all properties MultiValue is not 'false'");
            }
            if(piAllReferenceValue.isReadOnly() != false) {
                result.add("Property info for opendma:ReferenceValue in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","Object")).equals(piAllReferenceValue.getReferenceClass().getQName())) {
                result.add("Property info for opendma:ReferenceValue in all properties ReadOnly is not 'false'");
            }
            if(piAllReferenceValue.isHidden() != false) {
                result.add("Property info for opendma:ReferenceValue in all properties Hidden is not 'false'");
            }
            if(piAllReferenceValue.isRequired() != false) {
                result.add("Property info for opendma:ReferenceValue in all properties Required is not 'false'");
            }
            if(piAllReferenceValue.isSystem() != true) {
                result.add("Property info for opendma:ReferenceValue in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaRepository(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaRepository)) {
            result.add("Does not implement OdmaRepository interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Name
        OdmaQName qnameName = new OdmaQName("opendma","Name");
        try {
            OdmaProperty propName = obj.getProperty(qnameName);
            if(propName.getName() == null) {
                result.add("Property opendma:Name qname is null");
            }
            if(!"opendma".equals(propName.getName().getNamespace())) {
                result.add("Property opendma:Name qname namespace is not 'opendma', found instead'"+propName.getName().getNamespace()+"'");
            }
            if(!"Name".equals(propName.getName().getName())) {
                result.add("Property opendma:Name qname name is not 'Name', found instead'"+propName.getName().getName()+"'");
            }
            if(propName.getType() != OdmaType.STRING) {
                result.add("Property opendma:Name type is not 'STRING'");
            }
            if(propName.isMultiValue() != false) {
                result.add("Property opendma:Name MultiValue is not 'false'");
            }
            if(propName.getValue() == null) {
                result.add("Property opendma:Name is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Name");
        }
        if(clazz != null && (new OdmaQName("opendma","Repository")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameName.equals(pi.getQName())) {
                        if(piDeclaredName == null) {
                            piDeclaredName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Name");
                        }
                    }
                }
            }
            if(piDeclaredName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Name");
            }
            if(piDeclaredName != null) {
                if(!"opendma".equals(piDeclaredName.getNamespace())) {
                    result.add("Property info for opendma:Name in declared properties qname namespace is not 'opendma'");
                }
                if(!"Name".equals(piDeclaredName.getName())) {
                    result.add("Property info for opendma:Name in declared properties qname name is not 'Name'");
                }
                if(piDeclaredName.getDataType() != 1) {
                    result.add("Property info for opendma:Name in declared properties data type is not '1'");
                }
                if(piDeclaredName.isMultiValue() != false) {
                    result.add("Property info for opendma:Name in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredName.isReadOnly() != false) {
                    result.add("Property info for opendma:Name in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredName.isHidden() != false) {
                    result.add("Property info for opendma:Name in declared properties Hidden is not 'false'");
                }
                if(piDeclaredName.isRequired() != true) {
                result.add("Property info for opendma:Name in declared properties Required is not 'true'");
                }
                if(piDeclaredName.isSystem() != true) {
                    result.add("Property info for opendma:Name in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameName.equals(pi.getQName())) {
                    if(piAllName == null) {
                        piAllName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Name");
                    }
                }
            }
        }
        if(piAllName == null) {
            result.add("All properties in class have no property info object with qname opendma:Name");
        }
        if(piAllName != null) {
            if(!"opendma".equals(piAllName.getNamespace())) {
                result.add("Property info for opendma:Name in all properties qname namespace is not 'opendma'");
            }
            if(!"Name".equals(piAllName.getName())) {
                result.add("Property info for opendma:Name in all properties qname name is not 'Name'");
            }
            if(piAllName.getDataType() != 1) {
                result.add("Property info for opendma:Name in all properties data type is not '1'");
            }
            if(piAllName.isMultiValue() != false) {
                result.add("Property info for opendma:Name in all properties MultiValue is not 'false'");
            }
            if(piAllName.isReadOnly() != false) {
                result.add("Property info for opendma:Name in all properties ReadOnly is not 'false'");
            }
            if(piAllName.isHidden() != false) {
                result.add("Property info for opendma:Name in all properties Hidden is not 'false'");
            }
            if(piAllName.isRequired() != true) {
                result.add("Property info for opendma:Name in all properties Required is not 'true'");
            }
            if(piAllName.isSystem() != true) {
                result.add("Property info for opendma:Name in all properties System is not 'true'");
            }
        }
        // opendma:DisplayName
        OdmaQName qnameDisplayName = new OdmaQName("opendma","DisplayName");
        try {
            OdmaProperty propDisplayName = obj.getProperty(qnameDisplayName);
            if(propDisplayName.getName() == null) {
                result.add("Property opendma:DisplayName qname is null");
            }
            if(!"opendma".equals(propDisplayName.getName().getNamespace())) {
                result.add("Property opendma:DisplayName qname namespace is not 'opendma', found instead'"+propDisplayName.getName().getNamespace()+"'");
            }
            if(!"DisplayName".equals(propDisplayName.getName().getName())) {
                result.add("Property opendma:DisplayName qname name is not 'DisplayName', found instead'"+propDisplayName.getName().getName()+"'");
            }
            if(propDisplayName.getType() != OdmaType.STRING) {
                result.add("Property opendma:DisplayName type is not 'STRING'");
            }
            if(propDisplayName.isMultiValue() != false) {
                result.add("Property opendma:DisplayName MultiValue is not 'false'");
            }
            if(propDisplayName.getValue() == null) {
                result.add("Property opendma:DisplayName is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:DisplayName");
        }
        if(clazz != null && (new OdmaQName("opendma","Repository")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredDisplayName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameDisplayName.equals(pi.getQName())) {
                        if(piDeclaredDisplayName == null) {
                            piDeclaredDisplayName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:DisplayName");
                        }
                    }
                }
            }
            if(piDeclaredDisplayName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:DisplayName");
            }
            if(piDeclaredDisplayName != null) {
                if(!"opendma".equals(piDeclaredDisplayName.getNamespace())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname namespace is not 'opendma'");
                }
                if(!"DisplayName".equals(piDeclaredDisplayName.getName())) {
                    result.add("Property info for opendma:DisplayName in declared properties qname name is not 'DisplayName'");
                }
                if(piDeclaredDisplayName.getDataType() != 1) {
                    result.add("Property info for opendma:DisplayName in declared properties data type is not '1'");
                }
                if(piDeclaredDisplayName.isMultiValue() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredDisplayName.isReadOnly() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredDisplayName.isHidden() != false) {
                    result.add("Property info for opendma:DisplayName in declared properties Hidden is not 'false'");
                }
                if(piDeclaredDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in declared properties Required is not 'true'");
                }
                if(piDeclaredDisplayName.isSystem() != true) {
                    result.add("Property info for opendma:DisplayName in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllDisplayName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameDisplayName.equals(pi.getQName())) {
                    if(piAllDisplayName == null) {
                        piAllDisplayName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:DisplayName");
                    }
                }
            }
        }
        if(piAllDisplayName == null) {
            result.add("All properties in class have no property info object with qname opendma:DisplayName");
        }
        if(piAllDisplayName != null) {
            if(!"opendma".equals(piAllDisplayName.getNamespace())) {
                result.add("Property info for opendma:DisplayName in all properties qname namespace is not 'opendma'");
            }
            if(!"DisplayName".equals(piAllDisplayName.getName())) {
                result.add("Property info for opendma:DisplayName in all properties qname name is not 'DisplayName'");
            }
            if(piAllDisplayName.getDataType() != 1) {
                result.add("Property info for opendma:DisplayName in all properties data type is not '1'");
            }
            if(piAllDisplayName.isMultiValue() != false) {
                result.add("Property info for opendma:DisplayName in all properties MultiValue is not 'false'");
            }
            if(piAllDisplayName.isReadOnly() != false) {
                result.add("Property info for opendma:DisplayName in all properties ReadOnly is not 'false'");
            }
            if(piAllDisplayName.isHidden() != false) {
                result.add("Property info for opendma:DisplayName in all properties Hidden is not 'false'");
            }
            if(piAllDisplayName.isRequired() != true) {
                result.add("Property info for opendma:DisplayName in all properties Required is not 'true'");
            }
            if(piAllDisplayName.isSystem() != true) {
                result.add("Property info for opendma:DisplayName in all properties System is not 'true'");
            }
        }
        // opendma:RootClass
        OdmaQName qnameRootClass = new OdmaQName("opendma","RootClass");
        try {
            OdmaProperty propRootClass = obj.getProperty(qnameRootClass);
            if(propRootClass.getName() == null) {
                result.add("Property opendma:RootClass qname is null");
            }
            if(!"opendma".equals(propRootClass.getName().getNamespace())) {
                result.add("Property opendma:RootClass qname namespace is not 'opendma', found instead'"+propRootClass.getName().getNamespace()+"'");
            }
            if(!"RootClass".equals(propRootClass.getName().getName())) {
                result.add("Property opendma:RootClass qname name is not 'RootClass', found instead'"+propRootClass.getName().getName()+"'");
            }
            if(propRootClass.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:RootClass type is not 'REFERENCE'");
            }
            if(propRootClass.isMultiValue() != false) {
                result.add("Property opendma:RootClass MultiValue is not 'false'");
            }
            if(!propRootClass.isReadOnly()) {
                result.add("Property opendma:RootClass ReadOnly must be 'true'");
            }
            if(propRootClass.getValue() == null) {
                result.add("Property opendma:RootClass is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:RootClass");
        }
        if(clazz != null && (new OdmaQName("opendma","Repository")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredRootClass = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameRootClass.equals(pi.getQName())) {
                        if(piDeclaredRootClass == null) {
                            piDeclaredRootClass = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:RootClass");
                        }
                    }
                }
            }
            if(piDeclaredRootClass == null) {
                result.add("Declared properties in class have no property info object with qname opendma:RootClass");
            }
            if(piDeclaredRootClass != null) {
                if(!"opendma".equals(piDeclaredRootClass.getNamespace())) {
                    result.add("Property info for opendma:RootClass in declared properties qname namespace is not 'opendma'");
                }
                if(!"RootClass".equals(piDeclaredRootClass.getName())) {
                    result.add("Property info for opendma:RootClass in declared properties qname name is not 'RootClass'");
                }
                if(piDeclaredRootClass.getDataType() != 10) {
                    result.add("Property info for opendma:RootClass in declared properties data type is not '10'");
                }
                if(piDeclaredRootClass.isMultiValue() != false) {
                    result.add("Property info for opendma:RootClass in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredRootClass.isReadOnly() != true) {
                    result.add("Property info for opendma:RootClass in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Class")).equals(piDeclaredRootClass.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:RootClass in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredRootClass.isHidden() != false) {
                    result.add("Property info for opendma:RootClass in declared properties Hidden is not 'false'");
                }
                if(piDeclaredRootClass.isRequired() != true) {
                result.add("Property info for opendma:RootClass in declared properties Required is not 'true'");
                }
                if(piDeclaredRootClass.isSystem() != true) {
                    result.add("Property info for opendma:RootClass in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllRootClass = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameRootClass.equals(pi.getQName())) {
                    if(piAllRootClass == null) {
                        piAllRootClass = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:RootClass");
                    }
                }
            }
        }
        if(piAllRootClass == null) {
            result.add("All properties in class have no property info object with qname opendma:RootClass");
        }
        if(piAllRootClass != null) {
            if(!"opendma".equals(piAllRootClass.getNamespace())) {
                result.add("Property info for opendma:RootClass in all properties qname namespace is not 'opendma'");
            }
            if(!"RootClass".equals(piAllRootClass.getName())) {
                result.add("Property info for opendma:RootClass in all properties qname name is not 'RootClass'");
            }
            if(piAllRootClass.getDataType() != 10) {
                result.add("Property info for opendma:RootClass in all properties data type is not '10'");
            }
            if(piAllRootClass.isMultiValue() != false) {
                result.add("Property info for opendma:RootClass in all properties MultiValue is not 'false'");
            }
            if(piAllRootClass.isReadOnly() != true) {
                result.add("Property info for opendma:RootClass in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Class")).equals(piAllRootClass.getReferenceClass().getQName())) {
                result.add("Property info for opendma:RootClass in all properties ReadOnly is not 'true'");
            }
            if(piAllRootClass.isHidden() != false) {
                result.add("Property info for opendma:RootClass in all properties Hidden is not 'false'");
            }
            if(piAllRootClass.isRequired() != true) {
                result.add("Property info for opendma:RootClass in all properties Required is not 'true'");
            }
            if(piAllRootClass.isSystem() != true) {
                result.add("Property info for opendma:RootClass in all properties System is not 'true'");
            }
        }
        // opendma:RootAspects
        OdmaQName qnameRootAspects = new OdmaQName("opendma","RootAspects");
        try {
            OdmaProperty propRootAspects = obj.getProperty(qnameRootAspects);
            if(propRootAspects.getName() == null) {
                result.add("Property opendma:RootAspects qname is null");
            }
            if(!"opendma".equals(propRootAspects.getName().getNamespace())) {
                result.add("Property opendma:RootAspects qname namespace is not 'opendma', found instead'"+propRootAspects.getName().getNamespace()+"'");
            }
            if(!"RootAspects".equals(propRootAspects.getName().getName())) {
                result.add("Property opendma:RootAspects qname name is not 'RootAspects', found instead'"+propRootAspects.getName().getName()+"'");
            }
            if(propRootAspects.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:RootAspects type is not 'REFERENCE'");
            }
            if(propRootAspects.isMultiValue() != true) {
                result.add("Property opendma:RootAspects MultiValue is not 'true'");
            }
            if(!propRootAspects.isReadOnly()) {
                result.add("Property opendma:RootAspects ReadOnly must be 'true'");
            }
            if(propRootAspects.getValue() == null) {
                result.add("Property opendma:RootAspects is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:RootAspects");
        }
        if(clazz != null && (new OdmaQName("opendma","Repository")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredRootAspects = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameRootAspects.equals(pi.getQName())) {
                        if(piDeclaredRootAspects == null) {
                            piDeclaredRootAspects = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:RootAspects");
                        }
                    }
                }
            }
            if(piDeclaredRootAspects == null) {
                result.add("Declared properties in class have no property info object with qname opendma:RootAspects");
            }
            if(piDeclaredRootAspects != null) {
                if(!"opendma".equals(piDeclaredRootAspects.getNamespace())) {
                    result.add("Property info for opendma:RootAspects in declared properties qname namespace is not 'opendma'");
                }
                if(!"RootAspects".equals(piDeclaredRootAspects.getName())) {
                    result.add("Property info for opendma:RootAspects in declared properties qname name is not 'RootAspects'");
                }
                if(piDeclaredRootAspects.getDataType() != 10) {
                    result.add("Property info for opendma:RootAspects in declared properties data type is not '10'");
                }
                if(piDeclaredRootAspects.isMultiValue() != true) {
                    result.add("Property info for opendma:RootAspects in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredRootAspects.isReadOnly() != true) {
                    result.add("Property info for opendma:RootAspects in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Class")).equals(piDeclaredRootAspects.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:RootAspects in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredRootAspects.isHidden() != false) {
                    result.add("Property info for opendma:RootAspects in declared properties Hidden is not 'false'");
                }
                if(piDeclaredRootAspects.isRequired() != false) {
                result.add("Property info for opendma:RootAspects in declared properties Required is not 'false'");
                }
                if(piDeclaredRootAspects.isSystem() != true) {
                    result.add("Property info for opendma:RootAspects in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllRootAspects = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameRootAspects.equals(pi.getQName())) {
                    if(piAllRootAspects == null) {
                        piAllRootAspects = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:RootAspects");
                    }
                }
            }
        }
        if(piAllRootAspects == null) {
            result.add("All properties in class have no property info object with qname opendma:RootAspects");
        }
        if(piAllRootAspects != null) {
            if(!"opendma".equals(piAllRootAspects.getNamespace())) {
                result.add("Property info for opendma:RootAspects in all properties qname namespace is not 'opendma'");
            }
            if(!"RootAspects".equals(piAllRootAspects.getName())) {
                result.add("Property info for opendma:RootAspects in all properties qname name is not 'RootAspects'");
            }
            if(piAllRootAspects.getDataType() != 10) {
                result.add("Property info for opendma:RootAspects in all properties data type is not '10'");
            }
            if(piAllRootAspects.isMultiValue() != true) {
                result.add("Property info for opendma:RootAspects in all properties MultiValue is not 'true'");
            }
            if(piAllRootAspects.isReadOnly() != true) {
                result.add("Property info for opendma:RootAspects in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Class")).equals(piAllRootAspects.getReferenceClass().getQName())) {
                result.add("Property info for opendma:RootAspects in all properties ReadOnly is not 'true'");
            }
            if(piAllRootAspects.isHidden() != false) {
                result.add("Property info for opendma:RootAspects in all properties Hidden is not 'false'");
            }
            if(piAllRootAspects.isRequired() != false) {
                result.add("Property info for opendma:RootAspects in all properties Required is not 'false'");
            }
            if(piAllRootAspects.isSystem() != true) {
                result.add("Property info for opendma:RootAspects in all properties System is not 'true'");
            }
        }
        // opendma:RootFolder
        OdmaQName qnameRootFolder = new OdmaQName("opendma","RootFolder");
        try {
            OdmaProperty propRootFolder = obj.getProperty(qnameRootFolder);
            if(propRootFolder.getName() == null) {
                result.add("Property opendma:RootFolder qname is null");
            }
            if(!"opendma".equals(propRootFolder.getName().getNamespace())) {
                result.add("Property opendma:RootFolder qname namespace is not 'opendma', found instead'"+propRootFolder.getName().getNamespace()+"'");
            }
            if(!"RootFolder".equals(propRootFolder.getName().getName())) {
                result.add("Property opendma:RootFolder qname name is not 'RootFolder', found instead'"+propRootFolder.getName().getName()+"'");
            }
            if(propRootFolder.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:RootFolder type is not 'REFERENCE'");
            }
            if(propRootFolder.isMultiValue() != false) {
                result.add("Property opendma:RootFolder MultiValue is not 'false'");
            }
            if(!propRootFolder.isReadOnly()) {
                result.add("Property opendma:RootFolder ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:RootFolder");
        }
        if(clazz != null && (new OdmaQName("opendma","Repository")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredRootFolder = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameRootFolder.equals(pi.getQName())) {
                        if(piDeclaredRootFolder == null) {
                            piDeclaredRootFolder = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:RootFolder");
                        }
                    }
                }
            }
            if(piDeclaredRootFolder == null) {
                result.add("Declared properties in class have no property info object with qname opendma:RootFolder");
            }
            if(piDeclaredRootFolder != null) {
                if(!"opendma".equals(piDeclaredRootFolder.getNamespace())) {
                    result.add("Property info for opendma:RootFolder in declared properties qname namespace is not 'opendma'");
                }
                if(!"RootFolder".equals(piDeclaredRootFolder.getName())) {
                    result.add("Property info for opendma:RootFolder in declared properties qname name is not 'RootFolder'");
                }
                if(piDeclaredRootFolder.getDataType() != 10) {
                    result.add("Property info for opendma:RootFolder in declared properties data type is not '10'");
                }
                if(piDeclaredRootFolder.isMultiValue() != false) {
                    result.add("Property info for opendma:RootFolder in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredRootFolder.isReadOnly() != true) {
                    result.add("Property info for opendma:RootFolder in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Folder")).equals(piDeclaredRootFolder.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:RootFolder in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredRootFolder.isHidden() != false) {
                    result.add("Property info for opendma:RootFolder in declared properties Hidden is not 'false'");
                }
                if(piDeclaredRootFolder.isRequired() != false) {
                result.add("Property info for opendma:RootFolder in declared properties Required is not 'false'");
                }
                if(piDeclaredRootFolder.isSystem() != true) {
                    result.add("Property info for opendma:RootFolder in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllRootFolder = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameRootFolder.equals(pi.getQName())) {
                    if(piAllRootFolder == null) {
                        piAllRootFolder = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:RootFolder");
                    }
                }
            }
        }
        if(piAllRootFolder == null) {
            result.add("All properties in class have no property info object with qname opendma:RootFolder");
        }
        if(piAllRootFolder != null) {
            if(!"opendma".equals(piAllRootFolder.getNamespace())) {
                result.add("Property info for opendma:RootFolder in all properties qname namespace is not 'opendma'");
            }
            if(!"RootFolder".equals(piAllRootFolder.getName())) {
                result.add("Property info for opendma:RootFolder in all properties qname name is not 'RootFolder'");
            }
            if(piAllRootFolder.getDataType() != 10) {
                result.add("Property info for opendma:RootFolder in all properties data type is not '10'");
            }
            if(piAllRootFolder.isMultiValue() != false) {
                result.add("Property info for opendma:RootFolder in all properties MultiValue is not 'false'");
            }
            if(piAllRootFolder.isReadOnly() != true) {
                result.add("Property info for opendma:RootFolder in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Folder")).equals(piAllRootFolder.getReferenceClass().getQName())) {
                result.add("Property info for opendma:RootFolder in all properties ReadOnly is not 'true'");
            }
            if(piAllRootFolder.isHidden() != false) {
                result.add("Property info for opendma:RootFolder in all properties Hidden is not 'false'");
            }
            if(piAllRootFolder.isRequired() != false) {
                result.add("Property info for opendma:RootFolder in all properties Required is not 'false'");
            }
            if(piAllRootFolder.isSystem() != true) {
                result.add("Property info for opendma:RootFolder in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaDocument(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaDocument)) {
            result.add("Does not implement OdmaDocument interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Title
        OdmaQName qnameTitle = new OdmaQName("opendma","Title");
        try {
            OdmaProperty propTitle = obj.getProperty(qnameTitle);
            if(propTitle.getName() == null) {
                result.add("Property opendma:Title qname is null");
            }
            if(!"opendma".equals(propTitle.getName().getNamespace())) {
                result.add("Property opendma:Title qname namespace is not 'opendma', found instead'"+propTitle.getName().getNamespace()+"'");
            }
            if(!"Title".equals(propTitle.getName().getName())) {
                result.add("Property opendma:Title qname name is not 'Title', found instead'"+propTitle.getName().getName()+"'");
            }
            if(propTitle.getType() != OdmaType.STRING) {
                result.add("Property opendma:Title type is not 'STRING'");
            }
            if(propTitle.isMultiValue() != false) {
                result.add("Property opendma:Title MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Title");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredTitle = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameTitle.equals(pi.getQName())) {
                        if(piDeclaredTitle == null) {
                            piDeclaredTitle = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Title");
                        }
                    }
                }
            }
            if(piDeclaredTitle == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Title");
            }
            if(piDeclaredTitle != null) {
                if(!"opendma".equals(piDeclaredTitle.getNamespace())) {
                    result.add("Property info for opendma:Title in declared properties qname namespace is not 'opendma'");
                }
                if(!"Title".equals(piDeclaredTitle.getName())) {
                    result.add("Property info for opendma:Title in declared properties qname name is not 'Title'");
                }
                if(piDeclaredTitle.getDataType() != 1) {
                    result.add("Property info for opendma:Title in declared properties data type is not '1'");
                }
                if(piDeclaredTitle.isMultiValue() != false) {
                    result.add("Property info for opendma:Title in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredTitle.isReadOnly() != false) {
                    result.add("Property info for opendma:Title in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredTitle.isHidden() != false) {
                    result.add("Property info for opendma:Title in declared properties Hidden is not 'false'");
                }
                if(piDeclaredTitle.isRequired() != false) {
                result.add("Property info for opendma:Title in declared properties Required is not 'false'");
                }
                if(piDeclaredTitle.isSystem() != true) {
                    result.add("Property info for opendma:Title in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllTitle = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameTitle.equals(pi.getQName())) {
                    if(piAllTitle == null) {
                        piAllTitle = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Title");
                    }
                }
            }
        }
        if(piAllTitle == null) {
            result.add("All properties in class have no property info object with qname opendma:Title");
        }
        if(piAllTitle != null) {
            if(!"opendma".equals(piAllTitle.getNamespace())) {
                result.add("Property info for opendma:Title in all properties qname namespace is not 'opendma'");
            }
            if(!"Title".equals(piAllTitle.getName())) {
                result.add("Property info for opendma:Title in all properties qname name is not 'Title'");
            }
            if(piAllTitle.getDataType() != 1) {
                result.add("Property info for opendma:Title in all properties data type is not '1'");
            }
            if(piAllTitle.isMultiValue() != false) {
                result.add("Property info for opendma:Title in all properties MultiValue is not 'false'");
            }
            if(piAllTitle.isReadOnly() != false) {
                result.add("Property info for opendma:Title in all properties ReadOnly is not 'false'");
            }
            if(piAllTitle.isHidden() != false) {
                result.add("Property info for opendma:Title in all properties Hidden is not 'false'");
            }
            if(piAllTitle.isRequired() != false) {
                result.add("Property info for opendma:Title in all properties Required is not 'false'");
            }
            if(piAllTitle.isSystem() != true) {
                result.add("Property info for opendma:Title in all properties System is not 'true'");
            }
        }
        // opendma:Version
        OdmaQName qnameVersion = new OdmaQName("opendma","Version");
        try {
            OdmaProperty propVersion = obj.getProperty(qnameVersion);
            if(propVersion.getName() == null) {
                result.add("Property opendma:Version qname is null");
            }
            if(!"opendma".equals(propVersion.getName().getNamespace())) {
                result.add("Property opendma:Version qname namespace is not 'opendma', found instead'"+propVersion.getName().getNamespace()+"'");
            }
            if(!"Version".equals(propVersion.getName().getName())) {
                result.add("Property opendma:Version qname name is not 'Version', found instead'"+propVersion.getName().getName()+"'");
            }
            if(propVersion.getType() != OdmaType.STRING) {
                result.add("Property opendma:Version type is not 'STRING'");
            }
            if(propVersion.isMultiValue() != false) {
                result.add("Property opendma:Version MultiValue is not 'false'");
            }
            if(!propVersion.isReadOnly()) {
                result.add("Property opendma:Version ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Version");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredVersion = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameVersion.equals(pi.getQName())) {
                        if(piDeclaredVersion == null) {
                            piDeclaredVersion = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Version");
                        }
                    }
                }
            }
            if(piDeclaredVersion == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Version");
            }
            if(piDeclaredVersion != null) {
                if(!"opendma".equals(piDeclaredVersion.getNamespace())) {
                    result.add("Property info for opendma:Version in declared properties qname namespace is not 'opendma'");
                }
                if(!"Version".equals(piDeclaredVersion.getName())) {
                    result.add("Property info for opendma:Version in declared properties qname name is not 'Version'");
                }
                if(piDeclaredVersion.getDataType() != 1) {
                    result.add("Property info for opendma:Version in declared properties data type is not '1'");
                }
                if(piDeclaredVersion.isMultiValue() != false) {
                    result.add("Property info for opendma:Version in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredVersion.isReadOnly() != true) {
                    result.add("Property info for opendma:Version in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredVersion.isHidden() != false) {
                    result.add("Property info for opendma:Version in declared properties Hidden is not 'false'");
                }
                if(piDeclaredVersion.isRequired() != false) {
                result.add("Property info for opendma:Version in declared properties Required is not 'false'");
                }
                if(piDeclaredVersion.isSystem() != true) {
                    result.add("Property info for opendma:Version in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllVersion = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameVersion.equals(pi.getQName())) {
                    if(piAllVersion == null) {
                        piAllVersion = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Version");
                    }
                }
            }
        }
        if(piAllVersion == null) {
            result.add("All properties in class have no property info object with qname opendma:Version");
        }
        if(piAllVersion != null) {
            if(!"opendma".equals(piAllVersion.getNamespace())) {
                result.add("Property info for opendma:Version in all properties qname namespace is not 'opendma'");
            }
            if(!"Version".equals(piAllVersion.getName())) {
                result.add("Property info for opendma:Version in all properties qname name is not 'Version'");
            }
            if(piAllVersion.getDataType() != 1) {
                result.add("Property info for opendma:Version in all properties data type is not '1'");
            }
            if(piAllVersion.isMultiValue() != false) {
                result.add("Property info for opendma:Version in all properties MultiValue is not 'false'");
            }
            if(piAllVersion.isReadOnly() != true) {
                result.add("Property info for opendma:Version in all properties ReadOnly is not 'true'");
            }
            if(piAllVersion.isHidden() != false) {
                result.add("Property info for opendma:Version in all properties Hidden is not 'false'");
            }
            if(piAllVersion.isRequired() != false) {
                result.add("Property info for opendma:Version in all properties Required is not 'false'");
            }
            if(piAllVersion.isSystem() != true) {
                result.add("Property info for opendma:Version in all properties System is not 'true'");
            }
        }
        // opendma:VersionCollection
        OdmaQName qnameVersionCollection = new OdmaQName("opendma","VersionCollection");
        try {
            OdmaProperty propVersionCollection = obj.getProperty(qnameVersionCollection);
            if(propVersionCollection.getName() == null) {
                result.add("Property opendma:VersionCollection qname is null");
            }
            if(!"opendma".equals(propVersionCollection.getName().getNamespace())) {
                result.add("Property opendma:VersionCollection qname namespace is not 'opendma', found instead'"+propVersionCollection.getName().getNamespace()+"'");
            }
            if(!"VersionCollection".equals(propVersionCollection.getName().getName())) {
                result.add("Property opendma:VersionCollection qname name is not 'VersionCollection', found instead'"+propVersionCollection.getName().getName()+"'");
            }
            if(propVersionCollection.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:VersionCollection type is not 'REFERENCE'");
            }
            if(propVersionCollection.isMultiValue() != false) {
                result.add("Property opendma:VersionCollection MultiValue is not 'false'");
            }
            if(!propVersionCollection.isReadOnly()) {
                result.add("Property opendma:VersionCollection ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:VersionCollection");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredVersionCollection = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameVersionCollection.equals(pi.getQName())) {
                        if(piDeclaredVersionCollection == null) {
                            piDeclaredVersionCollection = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:VersionCollection");
                        }
                    }
                }
            }
            if(piDeclaredVersionCollection == null) {
                result.add("Declared properties in class have no property info object with qname opendma:VersionCollection");
            }
            if(piDeclaredVersionCollection != null) {
                if(!"opendma".equals(piDeclaredVersionCollection.getNamespace())) {
                    result.add("Property info for opendma:VersionCollection in declared properties qname namespace is not 'opendma'");
                }
                if(!"VersionCollection".equals(piDeclaredVersionCollection.getName())) {
                    result.add("Property info for opendma:VersionCollection in declared properties qname name is not 'VersionCollection'");
                }
                if(piDeclaredVersionCollection.getDataType() != 10) {
                    result.add("Property info for opendma:VersionCollection in declared properties data type is not '10'");
                }
                if(piDeclaredVersionCollection.isMultiValue() != false) {
                    result.add("Property info for opendma:VersionCollection in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredVersionCollection.isReadOnly() != true) {
                    result.add("Property info for opendma:VersionCollection in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","VersionCollection")).equals(piDeclaredVersionCollection.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:VersionCollection in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredVersionCollection.isHidden() != false) {
                    result.add("Property info for opendma:VersionCollection in declared properties Hidden is not 'false'");
                }
                if(piDeclaredVersionCollection.isRequired() != false) {
                result.add("Property info for opendma:VersionCollection in declared properties Required is not 'false'");
                }
                if(piDeclaredVersionCollection.isSystem() != true) {
                    result.add("Property info for opendma:VersionCollection in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllVersionCollection = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameVersionCollection.equals(pi.getQName())) {
                    if(piAllVersionCollection == null) {
                        piAllVersionCollection = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:VersionCollection");
                    }
                }
            }
        }
        if(piAllVersionCollection == null) {
            result.add("All properties in class have no property info object with qname opendma:VersionCollection");
        }
        if(piAllVersionCollection != null) {
            if(!"opendma".equals(piAllVersionCollection.getNamespace())) {
                result.add("Property info for opendma:VersionCollection in all properties qname namespace is not 'opendma'");
            }
            if(!"VersionCollection".equals(piAllVersionCollection.getName())) {
                result.add("Property info for opendma:VersionCollection in all properties qname name is not 'VersionCollection'");
            }
            if(piAllVersionCollection.getDataType() != 10) {
                result.add("Property info for opendma:VersionCollection in all properties data type is not '10'");
            }
            if(piAllVersionCollection.isMultiValue() != false) {
                result.add("Property info for opendma:VersionCollection in all properties MultiValue is not 'false'");
            }
            if(piAllVersionCollection.isReadOnly() != true) {
                result.add("Property info for opendma:VersionCollection in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","VersionCollection")).equals(piAllVersionCollection.getReferenceClass().getQName())) {
                result.add("Property info for opendma:VersionCollection in all properties ReadOnly is not 'true'");
            }
            if(piAllVersionCollection.isHidden() != false) {
                result.add("Property info for opendma:VersionCollection in all properties Hidden is not 'false'");
            }
            if(piAllVersionCollection.isRequired() != false) {
                result.add("Property info for opendma:VersionCollection in all properties Required is not 'false'");
            }
            if(piAllVersionCollection.isSystem() != true) {
                result.add("Property info for opendma:VersionCollection in all properties System is not 'true'");
            }
        }
        // opendma:VersionIndependentId
        OdmaQName qnameVersionIndependentId = new OdmaQName("opendma","VersionIndependentId");
        try {
            OdmaProperty propVersionIndependentId = obj.getProperty(qnameVersionIndependentId);
            if(propVersionIndependentId.getName() == null) {
                result.add("Property opendma:VersionIndependentId qname is null");
            }
            if(!"opendma".equals(propVersionIndependentId.getName().getNamespace())) {
                result.add("Property opendma:VersionIndependentId qname namespace is not 'opendma', found instead'"+propVersionIndependentId.getName().getNamespace()+"'");
            }
            if(!"VersionIndependentId".equals(propVersionIndependentId.getName().getName())) {
                result.add("Property opendma:VersionIndependentId qname name is not 'VersionIndependentId', found instead'"+propVersionIndependentId.getName().getName()+"'");
            }
            if(propVersionIndependentId.getType() != OdmaType.ID) {
                result.add("Property opendma:VersionIndependentId type is not 'ID'");
            }
            if(propVersionIndependentId.isMultiValue() != false) {
                result.add("Property opendma:VersionIndependentId MultiValue is not 'false'");
            }
            if(!propVersionIndependentId.isReadOnly()) {
                result.add("Property opendma:VersionIndependentId ReadOnly must be 'true'");
            }
            if(propVersionIndependentId.getValue() == null) {
                result.add("Property opendma:VersionIndependentId is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:VersionIndependentId");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredVersionIndependentId = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameVersionIndependentId.equals(pi.getQName())) {
                        if(piDeclaredVersionIndependentId == null) {
                            piDeclaredVersionIndependentId = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:VersionIndependentId");
                        }
                    }
                }
            }
            if(piDeclaredVersionIndependentId == null) {
                result.add("Declared properties in class have no property info object with qname opendma:VersionIndependentId");
            }
            if(piDeclaredVersionIndependentId != null) {
                if(!"opendma".equals(piDeclaredVersionIndependentId.getNamespace())) {
                    result.add("Property info for opendma:VersionIndependentId in declared properties qname namespace is not 'opendma'");
                }
                if(!"VersionIndependentId".equals(piDeclaredVersionIndependentId.getName())) {
                    result.add("Property info for opendma:VersionIndependentId in declared properties qname name is not 'VersionIndependentId'");
                }
                if(piDeclaredVersionIndependentId.getDataType() != 100) {
                    result.add("Property info for opendma:VersionIndependentId in declared properties data type is not '100'");
                }
                if(piDeclaredVersionIndependentId.isMultiValue() != false) {
                    result.add("Property info for opendma:VersionIndependentId in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredVersionIndependentId.isReadOnly() != true) {
                    result.add("Property info for opendma:VersionIndependentId in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredVersionIndependentId.isHidden() != false) {
                    result.add("Property info for opendma:VersionIndependentId in declared properties Hidden is not 'false'");
                }
                if(piDeclaredVersionIndependentId.isRequired() != true) {
                result.add("Property info for opendma:VersionIndependentId in declared properties Required is not 'true'");
                }
                if(piDeclaredVersionIndependentId.isSystem() != true) {
                    result.add("Property info for opendma:VersionIndependentId in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllVersionIndependentId = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameVersionIndependentId.equals(pi.getQName())) {
                    if(piAllVersionIndependentId == null) {
                        piAllVersionIndependentId = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:VersionIndependentId");
                    }
                }
            }
        }
        if(piAllVersionIndependentId == null) {
            result.add("All properties in class have no property info object with qname opendma:VersionIndependentId");
        }
        if(piAllVersionIndependentId != null) {
            if(!"opendma".equals(piAllVersionIndependentId.getNamespace())) {
                result.add("Property info for opendma:VersionIndependentId in all properties qname namespace is not 'opendma'");
            }
            if(!"VersionIndependentId".equals(piAllVersionIndependentId.getName())) {
                result.add("Property info for opendma:VersionIndependentId in all properties qname name is not 'VersionIndependentId'");
            }
            if(piAllVersionIndependentId.getDataType() != 100) {
                result.add("Property info for opendma:VersionIndependentId in all properties data type is not '100'");
            }
            if(piAllVersionIndependentId.isMultiValue() != false) {
                result.add("Property info for opendma:VersionIndependentId in all properties MultiValue is not 'false'");
            }
            if(piAllVersionIndependentId.isReadOnly() != true) {
                result.add("Property info for opendma:VersionIndependentId in all properties ReadOnly is not 'true'");
            }
            if(piAllVersionIndependentId.isHidden() != false) {
                result.add("Property info for opendma:VersionIndependentId in all properties Hidden is not 'false'");
            }
            if(piAllVersionIndependentId.isRequired() != true) {
                result.add("Property info for opendma:VersionIndependentId in all properties Required is not 'true'");
            }
            if(piAllVersionIndependentId.isSystem() != true) {
                result.add("Property info for opendma:VersionIndependentId in all properties System is not 'true'");
            }
        }
        // opendma:VersionIndependentGuid
        OdmaQName qnameVersionIndependentGuid = new OdmaQName("opendma","VersionIndependentGuid");
        try {
            OdmaProperty propVersionIndependentGuid = obj.getProperty(qnameVersionIndependentGuid);
            if(propVersionIndependentGuid.getName() == null) {
                result.add("Property opendma:VersionIndependentGuid qname is null");
            }
            if(!"opendma".equals(propVersionIndependentGuid.getName().getNamespace())) {
                result.add("Property opendma:VersionIndependentGuid qname namespace is not 'opendma', found instead'"+propVersionIndependentGuid.getName().getNamespace()+"'");
            }
            if(!"VersionIndependentGuid".equals(propVersionIndependentGuid.getName().getName())) {
                result.add("Property opendma:VersionIndependentGuid qname name is not 'VersionIndependentGuid', found instead'"+propVersionIndependentGuid.getName().getName()+"'");
            }
            if(propVersionIndependentGuid.getType() != OdmaType.GUID) {
                result.add("Property opendma:VersionIndependentGuid type is not 'GUID'");
            }
            if(propVersionIndependentGuid.isMultiValue() != false) {
                result.add("Property opendma:VersionIndependentGuid MultiValue is not 'false'");
            }
            if(!propVersionIndependentGuid.isReadOnly()) {
                result.add("Property opendma:VersionIndependentGuid ReadOnly must be 'true'");
            }
            if(propVersionIndependentGuid.getValue() == null) {
                result.add("Property opendma:VersionIndependentGuid is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:VersionIndependentGuid");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredVersionIndependentGuid = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameVersionIndependentGuid.equals(pi.getQName())) {
                        if(piDeclaredVersionIndependentGuid == null) {
                            piDeclaredVersionIndependentGuid = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:VersionIndependentGuid");
                        }
                    }
                }
            }
            if(piDeclaredVersionIndependentGuid == null) {
                result.add("Declared properties in class have no property info object with qname opendma:VersionIndependentGuid");
            }
            if(piDeclaredVersionIndependentGuid != null) {
                if(!"opendma".equals(piDeclaredVersionIndependentGuid.getNamespace())) {
                    result.add("Property info for opendma:VersionIndependentGuid in declared properties qname namespace is not 'opendma'");
                }
                if(!"VersionIndependentGuid".equals(piDeclaredVersionIndependentGuid.getName())) {
                    result.add("Property info for opendma:VersionIndependentGuid in declared properties qname name is not 'VersionIndependentGuid'");
                }
                if(piDeclaredVersionIndependentGuid.getDataType() != 101) {
                    result.add("Property info for opendma:VersionIndependentGuid in declared properties data type is not '101'");
                }
                if(piDeclaredVersionIndependentGuid.isMultiValue() != false) {
                    result.add("Property info for opendma:VersionIndependentGuid in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredVersionIndependentGuid.isReadOnly() != true) {
                    result.add("Property info for opendma:VersionIndependentGuid in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredVersionIndependentGuid.isHidden() != false) {
                    result.add("Property info for opendma:VersionIndependentGuid in declared properties Hidden is not 'false'");
                }
                if(piDeclaredVersionIndependentGuid.isRequired() != true) {
                result.add("Property info for opendma:VersionIndependentGuid in declared properties Required is not 'true'");
                }
                if(piDeclaredVersionIndependentGuid.isSystem() != true) {
                    result.add("Property info for opendma:VersionIndependentGuid in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllVersionIndependentGuid = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameVersionIndependentGuid.equals(pi.getQName())) {
                    if(piAllVersionIndependentGuid == null) {
                        piAllVersionIndependentGuid = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:VersionIndependentGuid");
                    }
                }
            }
        }
        if(piAllVersionIndependentGuid == null) {
            result.add("All properties in class have no property info object with qname opendma:VersionIndependentGuid");
        }
        if(piAllVersionIndependentGuid != null) {
            if(!"opendma".equals(piAllVersionIndependentGuid.getNamespace())) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties qname namespace is not 'opendma'");
            }
            if(!"VersionIndependentGuid".equals(piAllVersionIndependentGuid.getName())) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties qname name is not 'VersionIndependentGuid'");
            }
            if(piAllVersionIndependentGuid.getDataType() != 101) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties data type is not '101'");
            }
            if(piAllVersionIndependentGuid.isMultiValue() != false) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties MultiValue is not 'false'");
            }
            if(piAllVersionIndependentGuid.isReadOnly() != true) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties ReadOnly is not 'true'");
            }
            if(piAllVersionIndependentGuid.isHidden() != false) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties Hidden is not 'false'");
            }
            if(piAllVersionIndependentGuid.isRequired() != true) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties Required is not 'true'");
            }
            if(piAllVersionIndependentGuid.isSystem() != true) {
                result.add("Property info for opendma:VersionIndependentGuid in all properties System is not 'true'");
            }
        }
        // opendma:ContentElements
        OdmaQName qnameContentElements = new OdmaQName("opendma","ContentElements");
        try {
            OdmaProperty propContentElements = obj.getProperty(qnameContentElements);
            if(propContentElements.getName() == null) {
                result.add("Property opendma:ContentElements qname is null");
            }
            if(!"opendma".equals(propContentElements.getName().getNamespace())) {
                result.add("Property opendma:ContentElements qname namespace is not 'opendma', found instead'"+propContentElements.getName().getNamespace()+"'");
            }
            if(!"ContentElements".equals(propContentElements.getName().getName())) {
                result.add("Property opendma:ContentElements qname name is not 'ContentElements', found instead'"+propContentElements.getName().getName()+"'");
            }
            if(propContentElements.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:ContentElements type is not 'REFERENCE'");
            }
            if(propContentElements.isMultiValue() != true) {
                result.add("Property opendma:ContentElements MultiValue is not 'true'");
            }
            if(propContentElements.getValue() == null) {
                result.add("Property opendma:ContentElements is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ContentElements");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContentElements = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContentElements.equals(pi.getQName())) {
                        if(piDeclaredContentElements == null) {
                            piDeclaredContentElements = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ContentElements");
                        }
                    }
                }
            }
            if(piDeclaredContentElements == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ContentElements");
            }
            if(piDeclaredContentElements != null) {
                if(!"opendma".equals(piDeclaredContentElements.getNamespace())) {
                    result.add("Property info for opendma:ContentElements in declared properties qname namespace is not 'opendma'");
                }
                if(!"ContentElements".equals(piDeclaredContentElements.getName())) {
                    result.add("Property info for opendma:ContentElements in declared properties qname name is not 'ContentElements'");
                }
                if(piDeclaredContentElements.getDataType() != 10) {
                    result.add("Property info for opendma:ContentElements in declared properties data type is not '10'");
                }
                if(piDeclaredContentElements.isMultiValue() != true) {
                    result.add("Property info for opendma:ContentElements in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredContentElements.isReadOnly() != false) {
                    result.add("Property info for opendma:ContentElements in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","ContentElement")).equals(piDeclaredContentElements.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:ContentElements in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredContentElements.isHidden() != false) {
                    result.add("Property info for opendma:ContentElements in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContentElements.isRequired() != false) {
                result.add("Property info for opendma:ContentElements in declared properties Required is not 'false'");
                }
                if(piDeclaredContentElements.isSystem() != true) {
                    result.add("Property info for opendma:ContentElements in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContentElements = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContentElements.equals(pi.getQName())) {
                    if(piAllContentElements == null) {
                        piAllContentElements = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ContentElements");
                    }
                }
            }
        }
        if(piAllContentElements == null) {
            result.add("All properties in class have no property info object with qname opendma:ContentElements");
        }
        if(piAllContentElements != null) {
            if(!"opendma".equals(piAllContentElements.getNamespace())) {
                result.add("Property info for opendma:ContentElements in all properties qname namespace is not 'opendma'");
            }
            if(!"ContentElements".equals(piAllContentElements.getName())) {
                result.add("Property info for opendma:ContentElements in all properties qname name is not 'ContentElements'");
            }
            if(piAllContentElements.getDataType() != 10) {
                result.add("Property info for opendma:ContentElements in all properties data type is not '10'");
            }
            if(piAllContentElements.isMultiValue() != true) {
                result.add("Property info for opendma:ContentElements in all properties MultiValue is not 'true'");
            }
            if(piAllContentElements.isReadOnly() != false) {
                result.add("Property info for opendma:ContentElements in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","ContentElement")).equals(piAllContentElements.getReferenceClass().getQName())) {
                result.add("Property info for opendma:ContentElements in all properties ReadOnly is not 'false'");
            }
            if(piAllContentElements.isHidden() != false) {
                result.add("Property info for opendma:ContentElements in all properties Hidden is not 'false'");
            }
            if(piAllContentElements.isRequired() != false) {
                result.add("Property info for opendma:ContentElements in all properties Required is not 'false'");
            }
            if(piAllContentElements.isSystem() != true) {
                result.add("Property info for opendma:ContentElements in all properties System is not 'true'");
            }
        }
        // opendma:CombinedContentType
        OdmaQName qnameCombinedContentType = new OdmaQName("opendma","CombinedContentType");
        try {
            OdmaProperty propCombinedContentType = obj.getProperty(qnameCombinedContentType);
            if(propCombinedContentType.getName() == null) {
                result.add("Property opendma:CombinedContentType qname is null");
            }
            if(!"opendma".equals(propCombinedContentType.getName().getNamespace())) {
                result.add("Property opendma:CombinedContentType qname namespace is not 'opendma', found instead'"+propCombinedContentType.getName().getNamespace()+"'");
            }
            if(!"CombinedContentType".equals(propCombinedContentType.getName().getName())) {
                result.add("Property opendma:CombinedContentType qname name is not 'CombinedContentType', found instead'"+propCombinedContentType.getName().getName()+"'");
            }
            if(propCombinedContentType.getType() != OdmaType.STRING) {
                result.add("Property opendma:CombinedContentType type is not 'STRING'");
            }
            if(propCombinedContentType.isMultiValue() != false) {
                result.add("Property opendma:CombinedContentType MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CombinedContentType");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCombinedContentType = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCombinedContentType.equals(pi.getQName())) {
                        if(piDeclaredCombinedContentType == null) {
                            piDeclaredCombinedContentType = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CombinedContentType");
                        }
                    }
                }
            }
            if(piDeclaredCombinedContentType == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CombinedContentType");
            }
            if(piDeclaredCombinedContentType != null) {
                if(!"opendma".equals(piDeclaredCombinedContentType.getNamespace())) {
                    result.add("Property info for opendma:CombinedContentType in declared properties qname namespace is not 'opendma'");
                }
                if(!"CombinedContentType".equals(piDeclaredCombinedContentType.getName())) {
                    result.add("Property info for opendma:CombinedContentType in declared properties qname name is not 'CombinedContentType'");
                }
                if(piDeclaredCombinedContentType.getDataType() != 1) {
                    result.add("Property info for opendma:CombinedContentType in declared properties data type is not '1'");
                }
                if(piDeclaredCombinedContentType.isMultiValue() != false) {
                    result.add("Property info for opendma:CombinedContentType in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCombinedContentType.isReadOnly() != false) {
                    result.add("Property info for opendma:CombinedContentType in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredCombinedContentType.isHidden() != false) {
                    result.add("Property info for opendma:CombinedContentType in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCombinedContentType.isRequired() != false) {
                result.add("Property info for opendma:CombinedContentType in declared properties Required is not 'false'");
                }
                if(piDeclaredCombinedContentType.isSystem() != true) {
                    result.add("Property info for opendma:CombinedContentType in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCombinedContentType = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCombinedContentType.equals(pi.getQName())) {
                    if(piAllCombinedContentType == null) {
                        piAllCombinedContentType = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CombinedContentType");
                    }
                }
            }
        }
        if(piAllCombinedContentType == null) {
            result.add("All properties in class have no property info object with qname opendma:CombinedContentType");
        }
        if(piAllCombinedContentType != null) {
            if(!"opendma".equals(piAllCombinedContentType.getNamespace())) {
                result.add("Property info for opendma:CombinedContentType in all properties qname namespace is not 'opendma'");
            }
            if(!"CombinedContentType".equals(piAllCombinedContentType.getName())) {
                result.add("Property info for opendma:CombinedContentType in all properties qname name is not 'CombinedContentType'");
            }
            if(piAllCombinedContentType.getDataType() != 1) {
                result.add("Property info for opendma:CombinedContentType in all properties data type is not '1'");
            }
            if(piAllCombinedContentType.isMultiValue() != false) {
                result.add("Property info for opendma:CombinedContentType in all properties MultiValue is not 'false'");
            }
            if(piAllCombinedContentType.isReadOnly() != false) {
                result.add("Property info for opendma:CombinedContentType in all properties ReadOnly is not 'false'");
            }
            if(piAllCombinedContentType.isHidden() != false) {
                result.add("Property info for opendma:CombinedContentType in all properties Hidden is not 'false'");
            }
            if(piAllCombinedContentType.isRequired() != false) {
                result.add("Property info for opendma:CombinedContentType in all properties Required is not 'false'");
            }
            if(piAllCombinedContentType.isSystem() != true) {
                result.add("Property info for opendma:CombinedContentType in all properties System is not 'true'");
            }
        }
        // opendma:PrimaryContentElement
        OdmaQName qnamePrimaryContentElement = new OdmaQName("opendma","PrimaryContentElement");
        try {
            OdmaProperty propPrimaryContentElement = obj.getProperty(qnamePrimaryContentElement);
            if(propPrimaryContentElement.getName() == null) {
                result.add("Property opendma:PrimaryContentElement qname is null");
            }
            if(!"opendma".equals(propPrimaryContentElement.getName().getNamespace())) {
                result.add("Property opendma:PrimaryContentElement qname namespace is not 'opendma', found instead'"+propPrimaryContentElement.getName().getNamespace()+"'");
            }
            if(!"PrimaryContentElement".equals(propPrimaryContentElement.getName().getName())) {
                result.add("Property opendma:PrimaryContentElement qname name is not 'PrimaryContentElement', found instead'"+propPrimaryContentElement.getName().getName()+"'");
            }
            if(propPrimaryContentElement.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:PrimaryContentElement type is not 'REFERENCE'");
            }
            if(propPrimaryContentElement.isMultiValue() != false) {
                result.add("Property opendma:PrimaryContentElement MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:PrimaryContentElement");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredPrimaryContentElement = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnamePrimaryContentElement.equals(pi.getQName())) {
                        if(piDeclaredPrimaryContentElement == null) {
                            piDeclaredPrimaryContentElement = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:PrimaryContentElement");
                        }
                    }
                }
            }
            if(piDeclaredPrimaryContentElement == null) {
                result.add("Declared properties in class have no property info object with qname opendma:PrimaryContentElement");
            }
            if(piDeclaredPrimaryContentElement != null) {
                if(!"opendma".equals(piDeclaredPrimaryContentElement.getNamespace())) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties qname namespace is not 'opendma'");
                }
                if(!"PrimaryContentElement".equals(piDeclaredPrimaryContentElement.getName())) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties qname name is not 'PrimaryContentElement'");
                }
                if(piDeclaredPrimaryContentElement.getDataType() != 10) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties data type is not '10'");
                }
                if(piDeclaredPrimaryContentElement.isMultiValue() != false) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredPrimaryContentElement.isReadOnly() != false) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","ContentElement")).equals(piDeclaredPrimaryContentElement.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredPrimaryContentElement.isHidden() != false) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties Hidden is not 'false'");
                }
                if(piDeclaredPrimaryContentElement.isRequired() != false) {
                result.add("Property info for opendma:PrimaryContentElement in declared properties Required is not 'false'");
                }
                if(piDeclaredPrimaryContentElement.isSystem() != true) {
                    result.add("Property info for opendma:PrimaryContentElement in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllPrimaryContentElement = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnamePrimaryContentElement.equals(pi.getQName())) {
                    if(piAllPrimaryContentElement == null) {
                        piAllPrimaryContentElement = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:PrimaryContentElement");
                    }
                }
            }
        }
        if(piAllPrimaryContentElement == null) {
            result.add("All properties in class have no property info object with qname opendma:PrimaryContentElement");
        }
        if(piAllPrimaryContentElement != null) {
            if(!"opendma".equals(piAllPrimaryContentElement.getNamespace())) {
                result.add("Property info for opendma:PrimaryContentElement in all properties qname namespace is not 'opendma'");
            }
            if(!"PrimaryContentElement".equals(piAllPrimaryContentElement.getName())) {
                result.add("Property info for opendma:PrimaryContentElement in all properties qname name is not 'PrimaryContentElement'");
            }
            if(piAllPrimaryContentElement.getDataType() != 10) {
                result.add("Property info for opendma:PrimaryContentElement in all properties data type is not '10'");
            }
            if(piAllPrimaryContentElement.isMultiValue() != false) {
                result.add("Property info for opendma:PrimaryContentElement in all properties MultiValue is not 'false'");
            }
            if(piAllPrimaryContentElement.isReadOnly() != false) {
                result.add("Property info for opendma:PrimaryContentElement in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","ContentElement")).equals(piAllPrimaryContentElement.getReferenceClass().getQName())) {
                result.add("Property info for opendma:PrimaryContentElement in all properties ReadOnly is not 'false'");
            }
            if(piAllPrimaryContentElement.isHidden() != false) {
                result.add("Property info for opendma:PrimaryContentElement in all properties Hidden is not 'false'");
            }
            if(piAllPrimaryContentElement.isRequired() != false) {
                result.add("Property info for opendma:PrimaryContentElement in all properties Required is not 'false'");
            }
            if(piAllPrimaryContentElement.isSystem() != true) {
                result.add("Property info for opendma:PrimaryContentElement in all properties System is not 'true'");
            }
        }
        // opendma:CreatedAt
        OdmaQName qnameCreatedAt = new OdmaQName("opendma","CreatedAt");
        try {
            OdmaProperty propCreatedAt = obj.getProperty(qnameCreatedAt);
            if(propCreatedAt.getName() == null) {
                result.add("Property opendma:CreatedAt qname is null");
            }
            if(!"opendma".equals(propCreatedAt.getName().getNamespace())) {
                result.add("Property opendma:CreatedAt qname namespace is not 'opendma', found instead'"+propCreatedAt.getName().getNamespace()+"'");
            }
            if(!"CreatedAt".equals(propCreatedAt.getName().getName())) {
                result.add("Property opendma:CreatedAt qname name is not 'CreatedAt', found instead'"+propCreatedAt.getName().getName()+"'");
            }
            if(propCreatedAt.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:CreatedAt type is not 'DATETIME'");
            }
            if(propCreatedAt.isMultiValue() != false) {
                result.add("Property opendma:CreatedAt MultiValue is not 'false'");
            }
            if(!propCreatedAt.isReadOnly()) {
                result.add("Property opendma:CreatedAt ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CreatedAt");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCreatedAt = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCreatedAt.equals(pi.getQName())) {
                        if(piDeclaredCreatedAt == null) {
                            piDeclaredCreatedAt = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CreatedAt");
                        }
                    }
                }
            }
            if(piDeclaredCreatedAt == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CreatedAt");
            }
            if(piDeclaredCreatedAt != null) {
                if(!"opendma".equals(piDeclaredCreatedAt.getNamespace())) {
                    result.add("Property info for opendma:CreatedAt in declared properties qname namespace is not 'opendma'");
                }
                if(!"CreatedAt".equals(piDeclaredCreatedAt.getName())) {
                    result.add("Property info for opendma:CreatedAt in declared properties qname name is not 'CreatedAt'");
                }
                if(piDeclaredCreatedAt.getDataType() != 8) {
                    result.add("Property info for opendma:CreatedAt in declared properties data type is not '8'");
                }
                if(piDeclaredCreatedAt.isMultiValue() != false) {
                    result.add("Property info for opendma:CreatedAt in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCreatedAt.isReadOnly() != true) {
                    result.add("Property info for opendma:CreatedAt in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCreatedAt.isHidden() != false) {
                    result.add("Property info for opendma:CreatedAt in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCreatedAt.isRequired() != false) {
                result.add("Property info for opendma:CreatedAt in declared properties Required is not 'false'");
                }
                if(piDeclaredCreatedAt.isSystem() != true) {
                    result.add("Property info for opendma:CreatedAt in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCreatedAt = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCreatedAt.equals(pi.getQName())) {
                    if(piAllCreatedAt == null) {
                        piAllCreatedAt = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CreatedAt");
                    }
                }
            }
        }
        if(piAllCreatedAt == null) {
            result.add("All properties in class have no property info object with qname opendma:CreatedAt");
        }
        if(piAllCreatedAt != null) {
            if(!"opendma".equals(piAllCreatedAt.getNamespace())) {
                result.add("Property info for opendma:CreatedAt in all properties qname namespace is not 'opendma'");
            }
            if(!"CreatedAt".equals(piAllCreatedAt.getName())) {
                result.add("Property info for opendma:CreatedAt in all properties qname name is not 'CreatedAt'");
            }
            if(piAllCreatedAt.getDataType() != 8) {
                result.add("Property info for opendma:CreatedAt in all properties data type is not '8'");
            }
            if(piAllCreatedAt.isMultiValue() != false) {
                result.add("Property info for opendma:CreatedAt in all properties MultiValue is not 'false'");
            }
            if(piAllCreatedAt.isReadOnly() != true) {
                result.add("Property info for opendma:CreatedAt in all properties ReadOnly is not 'true'");
            }
            if(piAllCreatedAt.isHidden() != false) {
                result.add("Property info for opendma:CreatedAt in all properties Hidden is not 'false'");
            }
            if(piAllCreatedAt.isRequired() != false) {
                result.add("Property info for opendma:CreatedAt in all properties Required is not 'false'");
            }
            if(piAllCreatedAt.isSystem() != true) {
                result.add("Property info for opendma:CreatedAt in all properties System is not 'true'");
            }
        }
        // opendma:CreatedBy
        OdmaQName qnameCreatedBy = new OdmaQName("opendma","CreatedBy");
        try {
            OdmaProperty propCreatedBy = obj.getProperty(qnameCreatedBy);
            if(propCreatedBy.getName() == null) {
                result.add("Property opendma:CreatedBy qname is null");
            }
            if(!"opendma".equals(propCreatedBy.getName().getNamespace())) {
                result.add("Property opendma:CreatedBy qname namespace is not 'opendma', found instead'"+propCreatedBy.getName().getNamespace()+"'");
            }
            if(!"CreatedBy".equals(propCreatedBy.getName().getName())) {
                result.add("Property opendma:CreatedBy qname name is not 'CreatedBy', found instead'"+propCreatedBy.getName().getName()+"'");
            }
            if(propCreatedBy.getType() != OdmaType.STRING) {
                result.add("Property opendma:CreatedBy type is not 'STRING'");
            }
            if(propCreatedBy.isMultiValue() != false) {
                result.add("Property opendma:CreatedBy MultiValue is not 'false'");
            }
            if(!propCreatedBy.isReadOnly()) {
                result.add("Property opendma:CreatedBy ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CreatedBy");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCreatedBy = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCreatedBy.equals(pi.getQName())) {
                        if(piDeclaredCreatedBy == null) {
                            piDeclaredCreatedBy = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CreatedBy");
                        }
                    }
                }
            }
            if(piDeclaredCreatedBy == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CreatedBy");
            }
            if(piDeclaredCreatedBy != null) {
                if(!"opendma".equals(piDeclaredCreatedBy.getNamespace())) {
                    result.add("Property info for opendma:CreatedBy in declared properties qname namespace is not 'opendma'");
                }
                if(!"CreatedBy".equals(piDeclaredCreatedBy.getName())) {
                    result.add("Property info for opendma:CreatedBy in declared properties qname name is not 'CreatedBy'");
                }
                if(piDeclaredCreatedBy.getDataType() != 1) {
                    result.add("Property info for opendma:CreatedBy in declared properties data type is not '1'");
                }
                if(piDeclaredCreatedBy.isMultiValue() != false) {
                    result.add("Property info for opendma:CreatedBy in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCreatedBy.isReadOnly() != true) {
                    result.add("Property info for opendma:CreatedBy in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCreatedBy.isHidden() != false) {
                    result.add("Property info for opendma:CreatedBy in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCreatedBy.isRequired() != false) {
                result.add("Property info for opendma:CreatedBy in declared properties Required is not 'false'");
                }
                if(piDeclaredCreatedBy.isSystem() != true) {
                    result.add("Property info for opendma:CreatedBy in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCreatedBy = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCreatedBy.equals(pi.getQName())) {
                    if(piAllCreatedBy == null) {
                        piAllCreatedBy = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CreatedBy");
                    }
                }
            }
        }
        if(piAllCreatedBy == null) {
            result.add("All properties in class have no property info object with qname opendma:CreatedBy");
        }
        if(piAllCreatedBy != null) {
            if(!"opendma".equals(piAllCreatedBy.getNamespace())) {
                result.add("Property info for opendma:CreatedBy in all properties qname namespace is not 'opendma'");
            }
            if(!"CreatedBy".equals(piAllCreatedBy.getName())) {
                result.add("Property info for opendma:CreatedBy in all properties qname name is not 'CreatedBy'");
            }
            if(piAllCreatedBy.getDataType() != 1) {
                result.add("Property info for opendma:CreatedBy in all properties data type is not '1'");
            }
            if(piAllCreatedBy.isMultiValue() != false) {
                result.add("Property info for opendma:CreatedBy in all properties MultiValue is not 'false'");
            }
            if(piAllCreatedBy.isReadOnly() != true) {
                result.add("Property info for opendma:CreatedBy in all properties ReadOnly is not 'true'");
            }
            if(piAllCreatedBy.isHidden() != false) {
                result.add("Property info for opendma:CreatedBy in all properties Hidden is not 'false'");
            }
            if(piAllCreatedBy.isRequired() != false) {
                result.add("Property info for opendma:CreatedBy in all properties Required is not 'false'");
            }
            if(piAllCreatedBy.isSystem() != true) {
                result.add("Property info for opendma:CreatedBy in all properties System is not 'true'");
            }
        }
        // opendma:LastModifiedAt
        OdmaQName qnameLastModifiedAt = new OdmaQName("opendma","LastModifiedAt");
        try {
            OdmaProperty propLastModifiedAt = obj.getProperty(qnameLastModifiedAt);
            if(propLastModifiedAt.getName() == null) {
                result.add("Property opendma:LastModifiedAt qname is null");
            }
            if(!"opendma".equals(propLastModifiedAt.getName().getNamespace())) {
                result.add("Property opendma:LastModifiedAt qname namespace is not 'opendma', found instead'"+propLastModifiedAt.getName().getNamespace()+"'");
            }
            if(!"LastModifiedAt".equals(propLastModifiedAt.getName().getName())) {
                result.add("Property opendma:LastModifiedAt qname name is not 'LastModifiedAt', found instead'"+propLastModifiedAt.getName().getName()+"'");
            }
            if(propLastModifiedAt.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:LastModifiedAt type is not 'DATETIME'");
            }
            if(propLastModifiedAt.isMultiValue() != false) {
                result.add("Property opendma:LastModifiedAt MultiValue is not 'false'");
            }
            if(!propLastModifiedAt.isReadOnly()) {
                result.add("Property opendma:LastModifiedAt ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:LastModifiedAt");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLastModifiedAt = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLastModifiedAt.equals(pi.getQName())) {
                        if(piDeclaredLastModifiedAt == null) {
                            piDeclaredLastModifiedAt = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:LastModifiedAt");
                        }
                    }
                }
            }
            if(piDeclaredLastModifiedAt == null) {
                result.add("Declared properties in class have no property info object with qname opendma:LastModifiedAt");
            }
            if(piDeclaredLastModifiedAt != null) {
                if(!"opendma".equals(piDeclaredLastModifiedAt.getNamespace())) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties qname namespace is not 'opendma'");
                }
                if(!"LastModifiedAt".equals(piDeclaredLastModifiedAt.getName())) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties qname name is not 'LastModifiedAt'");
                }
                if(piDeclaredLastModifiedAt.getDataType() != 8) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties data type is not '8'");
                }
                if(piDeclaredLastModifiedAt.isMultiValue() != false) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isReadOnly() != true) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredLastModifiedAt.isHidden() != false) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedAt in declared properties Required is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isSystem() != true) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLastModifiedAt = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLastModifiedAt.equals(pi.getQName())) {
                    if(piAllLastModifiedAt == null) {
                        piAllLastModifiedAt = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:LastModifiedAt");
                    }
                }
            }
        }
        if(piAllLastModifiedAt == null) {
            result.add("All properties in class have no property info object with qname opendma:LastModifiedAt");
        }
        if(piAllLastModifiedAt != null) {
            if(!"opendma".equals(piAllLastModifiedAt.getNamespace())) {
                result.add("Property info for opendma:LastModifiedAt in all properties qname namespace is not 'opendma'");
            }
            if(!"LastModifiedAt".equals(piAllLastModifiedAt.getName())) {
                result.add("Property info for opendma:LastModifiedAt in all properties qname name is not 'LastModifiedAt'");
            }
            if(piAllLastModifiedAt.getDataType() != 8) {
                result.add("Property info for opendma:LastModifiedAt in all properties data type is not '8'");
            }
            if(piAllLastModifiedAt.isMultiValue() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties MultiValue is not 'false'");
            }
            if(piAllLastModifiedAt.isReadOnly() != true) {
                result.add("Property info for opendma:LastModifiedAt in all properties ReadOnly is not 'true'");
            }
            if(piAllLastModifiedAt.isHidden() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties Hidden is not 'false'");
            }
            if(piAllLastModifiedAt.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties Required is not 'false'");
            }
            if(piAllLastModifiedAt.isSystem() != true) {
                result.add("Property info for opendma:LastModifiedAt in all properties System is not 'true'");
            }
        }
        // opendma:LastModifiedBy
        OdmaQName qnameLastModifiedBy = new OdmaQName("opendma","LastModifiedBy");
        try {
            OdmaProperty propLastModifiedBy = obj.getProperty(qnameLastModifiedBy);
            if(propLastModifiedBy.getName() == null) {
                result.add("Property opendma:LastModifiedBy qname is null");
            }
            if(!"opendma".equals(propLastModifiedBy.getName().getNamespace())) {
                result.add("Property opendma:LastModifiedBy qname namespace is not 'opendma', found instead'"+propLastModifiedBy.getName().getNamespace()+"'");
            }
            if(!"LastModifiedBy".equals(propLastModifiedBy.getName().getName())) {
                result.add("Property opendma:LastModifiedBy qname name is not 'LastModifiedBy', found instead'"+propLastModifiedBy.getName().getName()+"'");
            }
            if(propLastModifiedBy.getType() != OdmaType.STRING) {
                result.add("Property opendma:LastModifiedBy type is not 'STRING'");
            }
            if(propLastModifiedBy.isMultiValue() != false) {
                result.add("Property opendma:LastModifiedBy MultiValue is not 'false'");
            }
            if(!propLastModifiedBy.isReadOnly()) {
                result.add("Property opendma:LastModifiedBy ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:LastModifiedBy");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLastModifiedBy = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLastModifiedBy.equals(pi.getQName())) {
                        if(piDeclaredLastModifiedBy == null) {
                            piDeclaredLastModifiedBy = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:LastModifiedBy");
                        }
                    }
                }
            }
            if(piDeclaredLastModifiedBy == null) {
                result.add("Declared properties in class have no property info object with qname opendma:LastModifiedBy");
            }
            if(piDeclaredLastModifiedBy != null) {
                if(!"opendma".equals(piDeclaredLastModifiedBy.getNamespace())) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties qname namespace is not 'opendma'");
                }
                if(!"LastModifiedBy".equals(piDeclaredLastModifiedBy.getName())) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties qname name is not 'LastModifiedBy'");
                }
                if(piDeclaredLastModifiedBy.getDataType() != 1) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties data type is not '1'");
                }
                if(piDeclaredLastModifiedBy.isMultiValue() != false) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isReadOnly() != true) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredLastModifiedBy.isHidden() != false) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedBy in declared properties Required is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isSystem() != true) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLastModifiedBy = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLastModifiedBy.equals(pi.getQName())) {
                    if(piAllLastModifiedBy == null) {
                        piAllLastModifiedBy = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:LastModifiedBy");
                    }
                }
            }
        }
        if(piAllLastModifiedBy == null) {
            result.add("All properties in class have no property info object with qname opendma:LastModifiedBy");
        }
        if(piAllLastModifiedBy != null) {
            if(!"opendma".equals(piAllLastModifiedBy.getNamespace())) {
                result.add("Property info for opendma:LastModifiedBy in all properties qname namespace is not 'opendma'");
            }
            if(!"LastModifiedBy".equals(piAllLastModifiedBy.getName())) {
                result.add("Property info for opendma:LastModifiedBy in all properties qname name is not 'LastModifiedBy'");
            }
            if(piAllLastModifiedBy.getDataType() != 1) {
                result.add("Property info for opendma:LastModifiedBy in all properties data type is not '1'");
            }
            if(piAllLastModifiedBy.isMultiValue() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties MultiValue is not 'false'");
            }
            if(piAllLastModifiedBy.isReadOnly() != true) {
                result.add("Property info for opendma:LastModifiedBy in all properties ReadOnly is not 'true'");
            }
            if(piAllLastModifiedBy.isHidden() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties Hidden is not 'false'");
            }
            if(piAllLastModifiedBy.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties Required is not 'false'");
            }
            if(piAllLastModifiedBy.isSystem() != true) {
                result.add("Property info for opendma:LastModifiedBy in all properties System is not 'true'");
            }
        }
        // opendma:CheckedOut
        OdmaQName qnameCheckedOut = new OdmaQName("opendma","CheckedOut");
        try {
            OdmaProperty propCheckedOut = obj.getProperty(qnameCheckedOut);
            if(propCheckedOut.getName() == null) {
                result.add("Property opendma:CheckedOut qname is null");
            }
            if(!"opendma".equals(propCheckedOut.getName().getNamespace())) {
                result.add("Property opendma:CheckedOut qname namespace is not 'opendma', found instead'"+propCheckedOut.getName().getNamespace()+"'");
            }
            if(!"CheckedOut".equals(propCheckedOut.getName().getName())) {
                result.add("Property opendma:CheckedOut qname name is not 'CheckedOut', found instead'"+propCheckedOut.getName().getName()+"'");
            }
            if(propCheckedOut.getType() != OdmaType.BOOLEAN) {
                result.add("Property opendma:CheckedOut type is not 'BOOLEAN'");
            }
            if(propCheckedOut.isMultiValue() != false) {
                result.add("Property opendma:CheckedOut MultiValue is not 'false'");
            }
            if(!propCheckedOut.isReadOnly()) {
                result.add("Property opendma:CheckedOut ReadOnly must be 'true'");
            }
            if(propCheckedOut.getValue() == null) {
                result.add("Property opendma:CheckedOut is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CheckedOut");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCheckedOut = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCheckedOut.equals(pi.getQName())) {
                        if(piDeclaredCheckedOut == null) {
                            piDeclaredCheckedOut = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CheckedOut");
                        }
                    }
                }
            }
            if(piDeclaredCheckedOut == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CheckedOut");
            }
            if(piDeclaredCheckedOut != null) {
                if(!"opendma".equals(piDeclaredCheckedOut.getNamespace())) {
                    result.add("Property info for opendma:CheckedOut in declared properties qname namespace is not 'opendma'");
                }
                if(!"CheckedOut".equals(piDeclaredCheckedOut.getName())) {
                    result.add("Property info for opendma:CheckedOut in declared properties qname name is not 'CheckedOut'");
                }
                if(piDeclaredCheckedOut.getDataType() != 7) {
                    result.add("Property info for opendma:CheckedOut in declared properties data type is not '7'");
                }
                if(piDeclaredCheckedOut.isMultiValue() != false) {
                    result.add("Property info for opendma:CheckedOut in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCheckedOut.isReadOnly() != true) {
                    result.add("Property info for opendma:CheckedOut in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCheckedOut.isHidden() != false) {
                    result.add("Property info for opendma:CheckedOut in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCheckedOut.isRequired() != true) {
                result.add("Property info for opendma:CheckedOut in declared properties Required is not 'true'");
                }
                if(piDeclaredCheckedOut.isSystem() != true) {
                    result.add("Property info for opendma:CheckedOut in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCheckedOut = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCheckedOut.equals(pi.getQName())) {
                    if(piAllCheckedOut == null) {
                        piAllCheckedOut = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CheckedOut");
                    }
                }
            }
        }
        if(piAllCheckedOut == null) {
            result.add("All properties in class have no property info object with qname opendma:CheckedOut");
        }
        if(piAllCheckedOut != null) {
            if(!"opendma".equals(piAllCheckedOut.getNamespace())) {
                result.add("Property info for opendma:CheckedOut in all properties qname namespace is not 'opendma'");
            }
            if(!"CheckedOut".equals(piAllCheckedOut.getName())) {
                result.add("Property info for opendma:CheckedOut in all properties qname name is not 'CheckedOut'");
            }
            if(piAllCheckedOut.getDataType() != 7) {
                result.add("Property info for opendma:CheckedOut in all properties data type is not '7'");
            }
            if(piAllCheckedOut.isMultiValue() != false) {
                result.add("Property info for opendma:CheckedOut in all properties MultiValue is not 'false'");
            }
            if(piAllCheckedOut.isReadOnly() != true) {
                result.add("Property info for opendma:CheckedOut in all properties ReadOnly is not 'true'");
            }
            if(piAllCheckedOut.isHidden() != false) {
                result.add("Property info for opendma:CheckedOut in all properties Hidden is not 'false'");
            }
            if(piAllCheckedOut.isRequired() != true) {
                result.add("Property info for opendma:CheckedOut in all properties Required is not 'true'");
            }
            if(piAllCheckedOut.isSystem() != true) {
                result.add("Property info for opendma:CheckedOut in all properties System is not 'true'");
            }
        }
        // opendma:CheckedOutAt
        OdmaQName qnameCheckedOutAt = new OdmaQName("opendma","CheckedOutAt");
        try {
            OdmaProperty propCheckedOutAt = obj.getProperty(qnameCheckedOutAt);
            if(propCheckedOutAt.getName() == null) {
                result.add("Property opendma:CheckedOutAt qname is null");
            }
            if(!"opendma".equals(propCheckedOutAt.getName().getNamespace())) {
                result.add("Property opendma:CheckedOutAt qname namespace is not 'opendma', found instead'"+propCheckedOutAt.getName().getNamespace()+"'");
            }
            if(!"CheckedOutAt".equals(propCheckedOutAt.getName().getName())) {
                result.add("Property opendma:CheckedOutAt qname name is not 'CheckedOutAt', found instead'"+propCheckedOutAt.getName().getName()+"'");
            }
            if(propCheckedOutAt.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:CheckedOutAt type is not 'DATETIME'");
            }
            if(propCheckedOutAt.isMultiValue() != false) {
                result.add("Property opendma:CheckedOutAt MultiValue is not 'false'");
            }
            if(!propCheckedOutAt.isReadOnly()) {
                result.add("Property opendma:CheckedOutAt ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CheckedOutAt");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCheckedOutAt = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCheckedOutAt.equals(pi.getQName())) {
                        if(piDeclaredCheckedOutAt == null) {
                            piDeclaredCheckedOutAt = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CheckedOutAt");
                        }
                    }
                }
            }
            if(piDeclaredCheckedOutAt == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CheckedOutAt");
            }
            if(piDeclaredCheckedOutAt != null) {
                if(!"opendma".equals(piDeclaredCheckedOutAt.getNamespace())) {
                    result.add("Property info for opendma:CheckedOutAt in declared properties qname namespace is not 'opendma'");
                }
                if(!"CheckedOutAt".equals(piDeclaredCheckedOutAt.getName())) {
                    result.add("Property info for opendma:CheckedOutAt in declared properties qname name is not 'CheckedOutAt'");
                }
                if(piDeclaredCheckedOutAt.getDataType() != 8) {
                    result.add("Property info for opendma:CheckedOutAt in declared properties data type is not '8'");
                }
                if(piDeclaredCheckedOutAt.isMultiValue() != false) {
                    result.add("Property info for opendma:CheckedOutAt in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCheckedOutAt.isReadOnly() != true) {
                    result.add("Property info for opendma:CheckedOutAt in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCheckedOutAt.isHidden() != false) {
                    result.add("Property info for opendma:CheckedOutAt in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCheckedOutAt.isRequired() != false) {
                result.add("Property info for opendma:CheckedOutAt in declared properties Required is not 'false'");
                }
                if(piDeclaredCheckedOutAt.isSystem() != true) {
                    result.add("Property info for opendma:CheckedOutAt in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCheckedOutAt = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCheckedOutAt.equals(pi.getQName())) {
                    if(piAllCheckedOutAt == null) {
                        piAllCheckedOutAt = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CheckedOutAt");
                    }
                }
            }
        }
        if(piAllCheckedOutAt == null) {
            result.add("All properties in class have no property info object with qname opendma:CheckedOutAt");
        }
        if(piAllCheckedOutAt != null) {
            if(!"opendma".equals(piAllCheckedOutAt.getNamespace())) {
                result.add("Property info for opendma:CheckedOutAt in all properties qname namespace is not 'opendma'");
            }
            if(!"CheckedOutAt".equals(piAllCheckedOutAt.getName())) {
                result.add("Property info for opendma:CheckedOutAt in all properties qname name is not 'CheckedOutAt'");
            }
            if(piAllCheckedOutAt.getDataType() != 8) {
                result.add("Property info for opendma:CheckedOutAt in all properties data type is not '8'");
            }
            if(piAllCheckedOutAt.isMultiValue() != false) {
                result.add("Property info for opendma:CheckedOutAt in all properties MultiValue is not 'false'");
            }
            if(piAllCheckedOutAt.isReadOnly() != true) {
                result.add("Property info for opendma:CheckedOutAt in all properties ReadOnly is not 'true'");
            }
            if(piAllCheckedOutAt.isHidden() != false) {
                result.add("Property info for opendma:CheckedOutAt in all properties Hidden is not 'false'");
            }
            if(piAllCheckedOutAt.isRequired() != false) {
                result.add("Property info for opendma:CheckedOutAt in all properties Required is not 'false'");
            }
            if(piAllCheckedOutAt.isSystem() != true) {
                result.add("Property info for opendma:CheckedOutAt in all properties System is not 'true'");
            }
        }
        // opendma:CheckedOutBy
        OdmaQName qnameCheckedOutBy = new OdmaQName("opendma","CheckedOutBy");
        try {
            OdmaProperty propCheckedOutBy = obj.getProperty(qnameCheckedOutBy);
            if(propCheckedOutBy.getName() == null) {
                result.add("Property opendma:CheckedOutBy qname is null");
            }
            if(!"opendma".equals(propCheckedOutBy.getName().getNamespace())) {
                result.add("Property opendma:CheckedOutBy qname namespace is not 'opendma', found instead'"+propCheckedOutBy.getName().getNamespace()+"'");
            }
            if(!"CheckedOutBy".equals(propCheckedOutBy.getName().getName())) {
                result.add("Property opendma:CheckedOutBy qname name is not 'CheckedOutBy', found instead'"+propCheckedOutBy.getName().getName()+"'");
            }
            if(propCheckedOutBy.getType() != OdmaType.STRING) {
                result.add("Property opendma:CheckedOutBy type is not 'STRING'");
            }
            if(propCheckedOutBy.isMultiValue() != false) {
                result.add("Property opendma:CheckedOutBy MultiValue is not 'false'");
            }
            if(!propCheckedOutBy.isReadOnly()) {
                result.add("Property opendma:CheckedOutBy ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CheckedOutBy");
        }
        if(clazz != null && (new OdmaQName("opendma","Document")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCheckedOutBy = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCheckedOutBy.equals(pi.getQName())) {
                        if(piDeclaredCheckedOutBy == null) {
                            piDeclaredCheckedOutBy = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CheckedOutBy");
                        }
                    }
                }
            }
            if(piDeclaredCheckedOutBy == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CheckedOutBy");
            }
            if(piDeclaredCheckedOutBy != null) {
                if(!"opendma".equals(piDeclaredCheckedOutBy.getNamespace())) {
                    result.add("Property info for opendma:CheckedOutBy in declared properties qname namespace is not 'opendma'");
                }
                if(!"CheckedOutBy".equals(piDeclaredCheckedOutBy.getName())) {
                    result.add("Property info for opendma:CheckedOutBy in declared properties qname name is not 'CheckedOutBy'");
                }
                if(piDeclaredCheckedOutBy.getDataType() != 1) {
                    result.add("Property info for opendma:CheckedOutBy in declared properties data type is not '1'");
                }
                if(piDeclaredCheckedOutBy.isMultiValue() != false) {
                    result.add("Property info for opendma:CheckedOutBy in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCheckedOutBy.isReadOnly() != true) {
                    result.add("Property info for opendma:CheckedOutBy in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCheckedOutBy.isHidden() != false) {
                    result.add("Property info for opendma:CheckedOutBy in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCheckedOutBy.isRequired() != false) {
                result.add("Property info for opendma:CheckedOutBy in declared properties Required is not 'false'");
                }
                if(piDeclaredCheckedOutBy.isSystem() != true) {
                    result.add("Property info for opendma:CheckedOutBy in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCheckedOutBy = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCheckedOutBy.equals(pi.getQName())) {
                    if(piAllCheckedOutBy == null) {
                        piAllCheckedOutBy = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CheckedOutBy");
                    }
                }
            }
        }
        if(piAllCheckedOutBy == null) {
            result.add("All properties in class have no property info object with qname opendma:CheckedOutBy");
        }
        if(piAllCheckedOutBy != null) {
            if(!"opendma".equals(piAllCheckedOutBy.getNamespace())) {
                result.add("Property info for opendma:CheckedOutBy in all properties qname namespace is not 'opendma'");
            }
            if(!"CheckedOutBy".equals(piAllCheckedOutBy.getName())) {
                result.add("Property info for opendma:CheckedOutBy in all properties qname name is not 'CheckedOutBy'");
            }
            if(piAllCheckedOutBy.getDataType() != 1) {
                result.add("Property info for opendma:CheckedOutBy in all properties data type is not '1'");
            }
            if(piAllCheckedOutBy.isMultiValue() != false) {
                result.add("Property info for opendma:CheckedOutBy in all properties MultiValue is not 'false'");
            }
            if(piAllCheckedOutBy.isReadOnly() != true) {
                result.add("Property info for opendma:CheckedOutBy in all properties ReadOnly is not 'true'");
            }
            if(piAllCheckedOutBy.isHidden() != false) {
                result.add("Property info for opendma:CheckedOutBy in all properties Hidden is not 'false'");
            }
            if(piAllCheckedOutBy.isRequired() != false) {
                result.add("Property info for opendma:CheckedOutBy in all properties Required is not 'false'");
            }
            if(piAllCheckedOutBy.isSystem() != true) {
                result.add("Property info for opendma:CheckedOutBy in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaContentElement(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaContentElement)) {
            result.add("Does not implement OdmaContentElement interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:ContentType
        OdmaQName qnameContentType = new OdmaQName("opendma","ContentType");
        try {
            OdmaProperty propContentType = obj.getProperty(qnameContentType);
            if(propContentType.getName() == null) {
                result.add("Property opendma:ContentType qname is null");
            }
            if(!"opendma".equals(propContentType.getName().getNamespace())) {
                result.add("Property opendma:ContentType qname namespace is not 'opendma', found instead'"+propContentType.getName().getNamespace()+"'");
            }
            if(!"ContentType".equals(propContentType.getName().getName())) {
                result.add("Property opendma:ContentType qname name is not 'ContentType', found instead'"+propContentType.getName().getName()+"'");
            }
            if(propContentType.getType() != OdmaType.STRING) {
                result.add("Property opendma:ContentType type is not 'STRING'");
            }
            if(propContentType.isMultiValue() != false) {
                result.add("Property opendma:ContentType MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ContentType");
        }
        if(clazz != null && (new OdmaQName("opendma","ContentElement")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContentType = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContentType.equals(pi.getQName())) {
                        if(piDeclaredContentType == null) {
                            piDeclaredContentType = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ContentType");
                        }
                    }
                }
            }
            if(piDeclaredContentType == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ContentType");
            }
            if(piDeclaredContentType != null) {
                if(!"opendma".equals(piDeclaredContentType.getNamespace())) {
                    result.add("Property info for opendma:ContentType in declared properties qname namespace is not 'opendma'");
                }
                if(!"ContentType".equals(piDeclaredContentType.getName())) {
                    result.add("Property info for opendma:ContentType in declared properties qname name is not 'ContentType'");
                }
                if(piDeclaredContentType.getDataType() != 1) {
                    result.add("Property info for opendma:ContentType in declared properties data type is not '1'");
                }
                if(piDeclaredContentType.isMultiValue() != false) {
                    result.add("Property info for opendma:ContentType in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredContentType.isReadOnly() != false) {
                    result.add("Property info for opendma:ContentType in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredContentType.isHidden() != false) {
                    result.add("Property info for opendma:ContentType in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContentType.isRequired() != false) {
                result.add("Property info for opendma:ContentType in declared properties Required is not 'false'");
                }
                if(piDeclaredContentType.isSystem() != true) {
                    result.add("Property info for opendma:ContentType in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContentType = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContentType.equals(pi.getQName())) {
                    if(piAllContentType == null) {
                        piAllContentType = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ContentType");
                    }
                }
            }
        }
        if(piAllContentType == null) {
            result.add("All properties in class have no property info object with qname opendma:ContentType");
        }
        if(piAllContentType != null) {
            if(!"opendma".equals(piAllContentType.getNamespace())) {
                result.add("Property info for opendma:ContentType in all properties qname namespace is not 'opendma'");
            }
            if(!"ContentType".equals(piAllContentType.getName())) {
                result.add("Property info for opendma:ContentType in all properties qname name is not 'ContentType'");
            }
            if(piAllContentType.getDataType() != 1) {
                result.add("Property info for opendma:ContentType in all properties data type is not '1'");
            }
            if(piAllContentType.isMultiValue() != false) {
                result.add("Property info for opendma:ContentType in all properties MultiValue is not 'false'");
            }
            if(piAllContentType.isReadOnly() != false) {
                result.add("Property info for opendma:ContentType in all properties ReadOnly is not 'false'");
            }
            if(piAllContentType.isHidden() != false) {
                result.add("Property info for opendma:ContentType in all properties Hidden is not 'false'");
            }
            if(piAllContentType.isRequired() != false) {
                result.add("Property info for opendma:ContentType in all properties Required is not 'false'");
            }
            if(piAllContentType.isSystem() != true) {
                result.add("Property info for opendma:ContentType in all properties System is not 'true'");
            }
        }
        // opendma:Position
        OdmaQName qnamePosition = new OdmaQName("opendma","Position");
        try {
            OdmaProperty propPosition = obj.getProperty(qnamePosition);
            if(propPosition.getName() == null) {
                result.add("Property opendma:Position qname is null");
            }
            if(!"opendma".equals(propPosition.getName().getNamespace())) {
                result.add("Property opendma:Position qname namespace is not 'opendma', found instead'"+propPosition.getName().getNamespace()+"'");
            }
            if(!"Position".equals(propPosition.getName().getName())) {
                result.add("Property opendma:Position qname name is not 'Position', found instead'"+propPosition.getName().getName()+"'");
            }
            if(propPosition.getType() != OdmaType.INTEGER) {
                result.add("Property opendma:Position type is not 'INTEGER'");
            }
            if(propPosition.isMultiValue() != false) {
                result.add("Property opendma:Position MultiValue is not 'false'");
            }
            if(!propPosition.isReadOnly()) {
                result.add("Property opendma:Position ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Position");
        }
        if(clazz != null && (new OdmaQName("opendma","ContentElement")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredPosition = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnamePosition.equals(pi.getQName())) {
                        if(piDeclaredPosition == null) {
                            piDeclaredPosition = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Position");
                        }
                    }
                }
            }
            if(piDeclaredPosition == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Position");
            }
            if(piDeclaredPosition != null) {
                if(!"opendma".equals(piDeclaredPosition.getNamespace())) {
                    result.add("Property info for opendma:Position in declared properties qname namespace is not 'opendma'");
                }
                if(!"Position".equals(piDeclaredPosition.getName())) {
                    result.add("Property info for opendma:Position in declared properties qname name is not 'Position'");
                }
                if(piDeclaredPosition.getDataType() != 2) {
                    result.add("Property info for opendma:Position in declared properties data type is not '2'");
                }
                if(piDeclaredPosition.isMultiValue() != false) {
                    result.add("Property info for opendma:Position in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredPosition.isReadOnly() != true) {
                    result.add("Property info for opendma:Position in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredPosition.isHidden() != false) {
                    result.add("Property info for opendma:Position in declared properties Hidden is not 'false'");
                }
                if(piDeclaredPosition.isRequired() != false) {
                result.add("Property info for opendma:Position in declared properties Required is not 'false'");
                }
                if(piDeclaredPosition.isSystem() != true) {
                    result.add("Property info for opendma:Position in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllPosition = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnamePosition.equals(pi.getQName())) {
                    if(piAllPosition == null) {
                        piAllPosition = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Position");
                    }
                }
            }
        }
        if(piAllPosition == null) {
            result.add("All properties in class have no property info object with qname opendma:Position");
        }
        if(piAllPosition != null) {
            if(!"opendma".equals(piAllPosition.getNamespace())) {
                result.add("Property info for opendma:Position in all properties qname namespace is not 'opendma'");
            }
            if(!"Position".equals(piAllPosition.getName())) {
                result.add("Property info for opendma:Position in all properties qname name is not 'Position'");
            }
            if(piAllPosition.getDataType() != 2) {
                result.add("Property info for opendma:Position in all properties data type is not '2'");
            }
            if(piAllPosition.isMultiValue() != false) {
                result.add("Property info for opendma:Position in all properties MultiValue is not 'false'");
            }
            if(piAllPosition.isReadOnly() != true) {
                result.add("Property info for opendma:Position in all properties ReadOnly is not 'true'");
            }
            if(piAllPosition.isHidden() != false) {
                result.add("Property info for opendma:Position in all properties Hidden is not 'false'");
            }
            if(piAllPosition.isRequired() != false) {
                result.add("Property info for opendma:Position in all properties Required is not 'false'");
            }
            if(piAllPosition.isSystem() != true) {
                result.add("Property info for opendma:Position in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaDataContentElement(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaDataContentElement)) {
            result.add("Does not implement OdmaDataContentElement interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaContentElement(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Content
        OdmaQName qnameContent = new OdmaQName("opendma","Content");
        try {
            OdmaProperty propContent = obj.getProperty(qnameContent);
            if(propContent.getName() == null) {
                result.add("Property opendma:Content qname is null");
            }
            if(!"opendma".equals(propContent.getName().getNamespace())) {
                result.add("Property opendma:Content qname namespace is not 'opendma', found instead'"+propContent.getName().getNamespace()+"'");
            }
            if(!"Content".equals(propContent.getName().getName())) {
                result.add("Property opendma:Content qname name is not 'Content', found instead'"+propContent.getName().getName()+"'");
            }
            if(propContent.getType() != OdmaType.CONTENT) {
                result.add("Property opendma:Content type is not 'CONTENT'");
            }
            if(propContent.isMultiValue() != false) {
                result.add("Property opendma:Content MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Content");
        }
        if(clazz != null && (new OdmaQName("opendma","DataContentElement")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContent = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContent.equals(pi.getQName())) {
                        if(piDeclaredContent == null) {
                            piDeclaredContent = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Content");
                        }
                    }
                }
            }
            if(piDeclaredContent == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Content");
            }
            if(piDeclaredContent != null) {
                if(!"opendma".equals(piDeclaredContent.getNamespace())) {
                    result.add("Property info for opendma:Content in declared properties qname namespace is not 'opendma'");
                }
                if(!"Content".equals(piDeclaredContent.getName())) {
                    result.add("Property info for opendma:Content in declared properties qname name is not 'Content'");
                }
                if(piDeclaredContent.getDataType() != 11) {
                    result.add("Property info for opendma:Content in declared properties data type is not '11'");
                }
                if(piDeclaredContent.isMultiValue() != false) {
                    result.add("Property info for opendma:Content in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredContent.isReadOnly() != false) {
                    result.add("Property info for opendma:Content in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredContent.isHidden() != false) {
                    result.add("Property info for opendma:Content in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContent.isRequired() != false) {
                result.add("Property info for opendma:Content in declared properties Required is not 'false'");
                }
                if(piDeclaredContent.isSystem() != true) {
                    result.add("Property info for opendma:Content in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContent = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContent.equals(pi.getQName())) {
                    if(piAllContent == null) {
                        piAllContent = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Content");
                    }
                }
            }
        }
        if(piAllContent == null) {
            result.add("All properties in class have no property info object with qname opendma:Content");
        }
        if(piAllContent != null) {
            if(!"opendma".equals(piAllContent.getNamespace())) {
                result.add("Property info for opendma:Content in all properties qname namespace is not 'opendma'");
            }
            if(!"Content".equals(piAllContent.getName())) {
                result.add("Property info for opendma:Content in all properties qname name is not 'Content'");
            }
            if(piAllContent.getDataType() != 11) {
                result.add("Property info for opendma:Content in all properties data type is not '11'");
            }
            if(piAllContent.isMultiValue() != false) {
                result.add("Property info for opendma:Content in all properties MultiValue is not 'false'");
            }
            if(piAllContent.isReadOnly() != false) {
                result.add("Property info for opendma:Content in all properties ReadOnly is not 'false'");
            }
            if(piAllContent.isHidden() != false) {
                result.add("Property info for opendma:Content in all properties Hidden is not 'false'");
            }
            if(piAllContent.isRequired() != false) {
                result.add("Property info for opendma:Content in all properties Required is not 'false'");
            }
            if(piAllContent.isSystem() != true) {
                result.add("Property info for opendma:Content in all properties System is not 'true'");
            }
        }
        // opendma:Size
        OdmaQName qnameSize = new OdmaQName("opendma","Size");
        try {
            OdmaProperty propSize = obj.getProperty(qnameSize);
            if(propSize.getName() == null) {
                result.add("Property opendma:Size qname is null");
            }
            if(!"opendma".equals(propSize.getName().getNamespace())) {
                result.add("Property opendma:Size qname namespace is not 'opendma', found instead'"+propSize.getName().getNamespace()+"'");
            }
            if(!"Size".equals(propSize.getName().getName())) {
                result.add("Property opendma:Size qname name is not 'Size', found instead'"+propSize.getName().getName()+"'");
            }
            if(propSize.getType() != OdmaType.LONG) {
                result.add("Property opendma:Size type is not 'LONG'");
            }
            if(propSize.isMultiValue() != false) {
                result.add("Property opendma:Size MultiValue is not 'false'");
            }
            if(!propSize.isReadOnly()) {
                result.add("Property opendma:Size ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Size");
        }
        if(clazz != null && (new OdmaQName("opendma","DataContentElement")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredSize = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameSize.equals(pi.getQName())) {
                        if(piDeclaredSize == null) {
                            piDeclaredSize = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Size");
                        }
                    }
                }
            }
            if(piDeclaredSize == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Size");
            }
            if(piDeclaredSize != null) {
                if(!"opendma".equals(piDeclaredSize.getNamespace())) {
                    result.add("Property info for opendma:Size in declared properties qname namespace is not 'opendma'");
                }
                if(!"Size".equals(piDeclaredSize.getName())) {
                    result.add("Property info for opendma:Size in declared properties qname name is not 'Size'");
                }
                if(piDeclaredSize.getDataType() != 4) {
                    result.add("Property info for opendma:Size in declared properties data type is not '4'");
                }
                if(piDeclaredSize.isMultiValue() != false) {
                    result.add("Property info for opendma:Size in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredSize.isReadOnly() != true) {
                    result.add("Property info for opendma:Size in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredSize.isHidden() != false) {
                    result.add("Property info for opendma:Size in declared properties Hidden is not 'false'");
                }
                if(piDeclaredSize.isRequired() != false) {
                result.add("Property info for opendma:Size in declared properties Required is not 'false'");
                }
                if(piDeclaredSize.isSystem() != true) {
                    result.add("Property info for opendma:Size in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllSize = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameSize.equals(pi.getQName())) {
                    if(piAllSize == null) {
                        piAllSize = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Size");
                    }
                }
            }
        }
        if(piAllSize == null) {
            result.add("All properties in class have no property info object with qname opendma:Size");
        }
        if(piAllSize != null) {
            if(!"opendma".equals(piAllSize.getNamespace())) {
                result.add("Property info for opendma:Size in all properties qname namespace is not 'opendma'");
            }
            if(!"Size".equals(piAllSize.getName())) {
                result.add("Property info for opendma:Size in all properties qname name is not 'Size'");
            }
            if(piAllSize.getDataType() != 4) {
                result.add("Property info for opendma:Size in all properties data type is not '4'");
            }
            if(piAllSize.isMultiValue() != false) {
                result.add("Property info for opendma:Size in all properties MultiValue is not 'false'");
            }
            if(piAllSize.isReadOnly() != true) {
                result.add("Property info for opendma:Size in all properties ReadOnly is not 'true'");
            }
            if(piAllSize.isHidden() != false) {
                result.add("Property info for opendma:Size in all properties Hidden is not 'false'");
            }
            if(piAllSize.isRequired() != false) {
                result.add("Property info for opendma:Size in all properties Required is not 'false'");
            }
            if(piAllSize.isSystem() != true) {
                result.add("Property info for opendma:Size in all properties System is not 'true'");
            }
        }
        // opendma:FileName
        OdmaQName qnameFileName = new OdmaQName("opendma","FileName");
        try {
            OdmaProperty propFileName = obj.getProperty(qnameFileName);
            if(propFileName.getName() == null) {
                result.add("Property opendma:FileName qname is null");
            }
            if(!"opendma".equals(propFileName.getName().getNamespace())) {
                result.add("Property opendma:FileName qname namespace is not 'opendma', found instead'"+propFileName.getName().getNamespace()+"'");
            }
            if(!"FileName".equals(propFileName.getName().getName())) {
                result.add("Property opendma:FileName qname name is not 'FileName', found instead'"+propFileName.getName().getName()+"'");
            }
            if(propFileName.getType() != OdmaType.STRING) {
                result.add("Property opendma:FileName type is not 'STRING'");
            }
            if(propFileName.isMultiValue() != false) {
                result.add("Property opendma:FileName MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:FileName");
        }
        if(clazz != null && (new OdmaQName("opendma","DataContentElement")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredFileName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameFileName.equals(pi.getQName())) {
                        if(piDeclaredFileName == null) {
                            piDeclaredFileName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:FileName");
                        }
                    }
                }
            }
            if(piDeclaredFileName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:FileName");
            }
            if(piDeclaredFileName != null) {
                if(!"opendma".equals(piDeclaredFileName.getNamespace())) {
                    result.add("Property info for opendma:FileName in declared properties qname namespace is not 'opendma'");
                }
                if(!"FileName".equals(piDeclaredFileName.getName())) {
                    result.add("Property info for opendma:FileName in declared properties qname name is not 'FileName'");
                }
                if(piDeclaredFileName.getDataType() != 1) {
                    result.add("Property info for opendma:FileName in declared properties data type is not '1'");
                }
                if(piDeclaredFileName.isMultiValue() != false) {
                    result.add("Property info for opendma:FileName in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredFileName.isReadOnly() != false) {
                    result.add("Property info for opendma:FileName in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredFileName.isHidden() != false) {
                    result.add("Property info for opendma:FileName in declared properties Hidden is not 'false'");
                }
                if(piDeclaredFileName.isRequired() != false) {
                result.add("Property info for opendma:FileName in declared properties Required is not 'false'");
                }
                if(piDeclaredFileName.isSystem() != true) {
                    result.add("Property info for opendma:FileName in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllFileName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameFileName.equals(pi.getQName())) {
                    if(piAllFileName == null) {
                        piAllFileName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:FileName");
                    }
                }
            }
        }
        if(piAllFileName == null) {
            result.add("All properties in class have no property info object with qname opendma:FileName");
        }
        if(piAllFileName != null) {
            if(!"opendma".equals(piAllFileName.getNamespace())) {
                result.add("Property info for opendma:FileName in all properties qname namespace is not 'opendma'");
            }
            if(!"FileName".equals(piAllFileName.getName())) {
                result.add("Property info for opendma:FileName in all properties qname name is not 'FileName'");
            }
            if(piAllFileName.getDataType() != 1) {
                result.add("Property info for opendma:FileName in all properties data type is not '1'");
            }
            if(piAllFileName.isMultiValue() != false) {
                result.add("Property info for opendma:FileName in all properties MultiValue is not 'false'");
            }
            if(piAllFileName.isReadOnly() != false) {
                result.add("Property info for opendma:FileName in all properties ReadOnly is not 'false'");
            }
            if(piAllFileName.isHidden() != false) {
                result.add("Property info for opendma:FileName in all properties Hidden is not 'false'");
            }
            if(piAllFileName.isRequired() != false) {
                result.add("Property info for opendma:FileName in all properties Required is not 'false'");
            }
            if(piAllFileName.isSystem() != true) {
                result.add("Property info for opendma:FileName in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaReferenceContentElement(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaReferenceContentElement)) {
            result.add("Does not implement OdmaReferenceContentElement interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaContentElement(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Location
        OdmaQName qnameLocation = new OdmaQName("opendma","Location");
        try {
            OdmaProperty propLocation = obj.getProperty(qnameLocation);
            if(propLocation.getName() == null) {
                result.add("Property opendma:Location qname is null");
            }
            if(!"opendma".equals(propLocation.getName().getNamespace())) {
                result.add("Property opendma:Location qname namespace is not 'opendma', found instead'"+propLocation.getName().getNamespace()+"'");
            }
            if(!"Location".equals(propLocation.getName().getName())) {
                result.add("Property opendma:Location qname name is not 'Location', found instead'"+propLocation.getName().getName()+"'");
            }
            if(propLocation.getType() != OdmaType.STRING) {
                result.add("Property opendma:Location type is not 'STRING'");
            }
            if(propLocation.isMultiValue() != false) {
                result.add("Property opendma:Location MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Location");
        }
        if(clazz != null && (new OdmaQName("opendma","ReferenceContentElement")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLocation = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLocation.equals(pi.getQName())) {
                        if(piDeclaredLocation == null) {
                            piDeclaredLocation = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Location");
                        }
                    }
                }
            }
            if(piDeclaredLocation == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Location");
            }
            if(piDeclaredLocation != null) {
                if(!"opendma".equals(piDeclaredLocation.getNamespace())) {
                    result.add("Property info for opendma:Location in declared properties qname namespace is not 'opendma'");
                }
                if(!"Location".equals(piDeclaredLocation.getName())) {
                    result.add("Property info for opendma:Location in declared properties qname name is not 'Location'");
                }
                if(piDeclaredLocation.getDataType() != 1) {
                    result.add("Property info for opendma:Location in declared properties data type is not '1'");
                }
                if(piDeclaredLocation.isMultiValue() != false) {
                    result.add("Property info for opendma:Location in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLocation.isReadOnly() != false) {
                    result.add("Property info for opendma:Location in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredLocation.isHidden() != false) {
                    result.add("Property info for opendma:Location in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLocation.isRequired() != false) {
                result.add("Property info for opendma:Location in declared properties Required is not 'false'");
                }
                if(piDeclaredLocation.isSystem() != true) {
                    result.add("Property info for opendma:Location in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLocation = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLocation.equals(pi.getQName())) {
                    if(piAllLocation == null) {
                        piAllLocation = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Location");
                    }
                }
            }
        }
        if(piAllLocation == null) {
            result.add("All properties in class have no property info object with qname opendma:Location");
        }
        if(piAllLocation != null) {
            if(!"opendma".equals(piAllLocation.getNamespace())) {
                result.add("Property info for opendma:Location in all properties qname namespace is not 'opendma'");
            }
            if(!"Location".equals(piAllLocation.getName())) {
                result.add("Property info for opendma:Location in all properties qname name is not 'Location'");
            }
            if(piAllLocation.getDataType() != 1) {
                result.add("Property info for opendma:Location in all properties data type is not '1'");
            }
            if(piAllLocation.isMultiValue() != false) {
                result.add("Property info for opendma:Location in all properties MultiValue is not 'false'");
            }
            if(piAllLocation.isReadOnly() != false) {
                result.add("Property info for opendma:Location in all properties ReadOnly is not 'false'");
            }
            if(piAllLocation.isHidden() != false) {
                result.add("Property info for opendma:Location in all properties Hidden is not 'false'");
            }
            if(piAllLocation.isRequired() != false) {
                result.add("Property info for opendma:Location in all properties Required is not 'false'");
            }
            if(piAllLocation.isSystem() != true) {
                result.add("Property info for opendma:Location in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaVersionCollection(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaVersionCollection)) {
            result.add("Does not implement OdmaVersionCollection interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Versions
        OdmaQName qnameVersions = new OdmaQName("opendma","Versions");
        try {
            OdmaProperty propVersions = obj.getProperty(qnameVersions);
            if(propVersions.getName() == null) {
                result.add("Property opendma:Versions qname is null");
            }
            if(!"opendma".equals(propVersions.getName().getNamespace())) {
                result.add("Property opendma:Versions qname namespace is not 'opendma', found instead'"+propVersions.getName().getNamespace()+"'");
            }
            if(!"Versions".equals(propVersions.getName().getName())) {
                result.add("Property opendma:Versions qname name is not 'Versions', found instead'"+propVersions.getName().getName()+"'");
            }
            if(propVersions.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Versions type is not 'REFERENCE'");
            }
            if(propVersions.isMultiValue() != true) {
                result.add("Property opendma:Versions MultiValue is not 'true'");
            }
            if(!propVersions.isReadOnly()) {
                result.add("Property opendma:Versions ReadOnly must be 'true'");
            }
            if(propVersions.getValue() == null) {
                result.add("Property opendma:Versions is multi-valued but value is null");
            }
            try {
                if(!propVersions.getReferenceIterable().iterator().hasNext()) {
                    result.add("Property opendma:Versions is required but value is empty");
                }
            } catch(OdmaInvalidDataTypeException idte) {
                result.add("OdmaInvalidDataTypeException accessing value of property opendma:Versions");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Versions");
        }
        if(clazz != null && (new OdmaQName("opendma","VersionCollection")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredVersions = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameVersions.equals(pi.getQName())) {
                        if(piDeclaredVersions == null) {
                            piDeclaredVersions = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Versions");
                        }
                    }
                }
            }
            if(piDeclaredVersions == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Versions");
            }
            if(piDeclaredVersions != null) {
                if(!"opendma".equals(piDeclaredVersions.getNamespace())) {
                    result.add("Property info for opendma:Versions in declared properties qname namespace is not 'opendma'");
                }
                if(!"Versions".equals(piDeclaredVersions.getName())) {
                    result.add("Property info for opendma:Versions in declared properties qname name is not 'Versions'");
                }
                if(piDeclaredVersions.getDataType() != 10) {
                    result.add("Property info for opendma:Versions in declared properties data type is not '10'");
                }
                if(piDeclaredVersions.isMultiValue() != true) {
                    result.add("Property info for opendma:Versions in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredVersions.isReadOnly() != true) {
                    result.add("Property info for opendma:Versions in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Document")).equals(piDeclaredVersions.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Versions in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredVersions.isHidden() != false) {
                    result.add("Property info for opendma:Versions in declared properties Hidden is not 'false'");
                }
                if(piDeclaredVersions.isRequired() != true) {
                result.add("Property info for opendma:Versions in declared properties Required is not 'true'");
                }
                if(piDeclaredVersions.isSystem() != true) {
                    result.add("Property info for opendma:Versions in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllVersions = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameVersions.equals(pi.getQName())) {
                    if(piAllVersions == null) {
                        piAllVersions = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Versions");
                    }
                }
            }
        }
        if(piAllVersions == null) {
            result.add("All properties in class have no property info object with qname opendma:Versions");
        }
        if(piAllVersions != null) {
            if(!"opendma".equals(piAllVersions.getNamespace())) {
                result.add("Property info for opendma:Versions in all properties qname namespace is not 'opendma'");
            }
            if(!"Versions".equals(piAllVersions.getName())) {
                result.add("Property info for opendma:Versions in all properties qname name is not 'Versions'");
            }
            if(piAllVersions.getDataType() != 10) {
                result.add("Property info for opendma:Versions in all properties data type is not '10'");
            }
            if(piAllVersions.isMultiValue() != true) {
                result.add("Property info for opendma:Versions in all properties MultiValue is not 'true'");
            }
            if(piAllVersions.isReadOnly() != true) {
                result.add("Property info for opendma:Versions in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Document")).equals(piAllVersions.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Versions in all properties ReadOnly is not 'true'");
            }
            if(piAllVersions.isHidden() != false) {
                result.add("Property info for opendma:Versions in all properties Hidden is not 'false'");
            }
            if(piAllVersions.isRequired() != true) {
                result.add("Property info for opendma:Versions in all properties Required is not 'true'");
            }
            if(piAllVersions.isSystem() != true) {
                result.add("Property info for opendma:Versions in all properties System is not 'true'");
            }
        }
        // opendma:Latest
        OdmaQName qnameLatest = new OdmaQName("opendma","Latest");
        try {
            OdmaProperty propLatest = obj.getProperty(qnameLatest);
            if(propLatest.getName() == null) {
                result.add("Property opendma:Latest qname is null");
            }
            if(!"opendma".equals(propLatest.getName().getNamespace())) {
                result.add("Property opendma:Latest qname namespace is not 'opendma', found instead'"+propLatest.getName().getNamespace()+"'");
            }
            if(!"Latest".equals(propLatest.getName().getName())) {
                result.add("Property opendma:Latest qname name is not 'Latest', found instead'"+propLatest.getName().getName()+"'");
            }
            if(propLatest.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Latest type is not 'REFERENCE'");
            }
            if(propLatest.isMultiValue() != false) {
                result.add("Property opendma:Latest MultiValue is not 'false'");
            }
            if(!propLatest.isReadOnly()) {
                result.add("Property opendma:Latest ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Latest");
        }
        if(clazz != null && (new OdmaQName("opendma","VersionCollection")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLatest = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLatest.equals(pi.getQName())) {
                        if(piDeclaredLatest == null) {
                            piDeclaredLatest = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Latest");
                        }
                    }
                }
            }
            if(piDeclaredLatest == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Latest");
            }
            if(piDeclaredLatest != null) {
                if(!"opendma".equals(piDeclaredLatest.getNamespace())) {
                    result.add("Property info for opendma:Latest in declared properties qname namespace is not 'opendma'");
                }
                if(!"Latest".equals(piDeclaredLatest.getName())) {
                    result.add("Property info for opendma:Latest in declared properties qname name is not 'Latest'");
                }
                if(piDeclaredLatest.getDataType() != 10) {
                    result.add("Property info for opendma:Latest in declared properties data type is not '10'");
                }
                if(piDeclaredLatest.isMultiValue() != false) {
                    result.add("Property info for opendma:Latest in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLatest.isReadOnly() != true) {
                    result.add("Property info for opendma:Latest in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Document")).equals(piDeclaredLatest.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Latest in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredLatest.isHidden() != false) {
                    result.add("Property info for opendma:Latest in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLatest.isRequired() != false) {
                result.add("Property info for opendma:Latest in declared properties Required is not 'false'");
                }
                if(piDeclaredLatest.isSystem() != true) {
                    result.add("Property info for opendma:Latest in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLatest = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLatest.equals(pi.getQName())) {
                    if(piAllLatest == null) {
                        piAllLatest = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Latest");
                    }
                }
            }
        }
        if(piAllLatest == null) {
            result.add("All properties in class have no property info object with qname opendma:Latest");
        }
        if(piAllLatest != null) {
            if(!"opendma".equals(piAllLatest.getNamespace())) {
                result.add("Property info for opendma:Latest in all properties qname namespace is not 'opendma'");
            }
            if(!"Latest".equals(piAllLatest.getName())) {
                result.add("Property info for opendma:Latest in all properties qname name is not 'Latest'");
            }
            if(piAllLatest.getDataType() != 10) {
                result.add("Property info for opendma:Latest in all properties data type is not '10'");
            }
            if(piAllLatest.isMultiValue() != false) {
                result.add("Property info for opendma:Latest in all properties MultiValue is not 'false'");
            }
            if(piAllLatest.isReadOnly() != true) {
                result.add("Property info for opendma:Latest in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Document")).equals(piAllLatest.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Latest in all properties ReadOnly is not 'true'");
            }
            if(piAllLatest.isHidden() != false) {
                result.add("Property info for opendma:Latest in all properties Hidden is not 'false'");
            }
            if(piAllLatest.isRequired() != false) {
                result.add("Property info for opendma:Latest in all properties Required is not 'false'");
            }
            if(piAllLatest.isSystem() != true) {
                result.add("Property info for opendma:Latest in all properties System is not 'true'");
            }
        }
        // opendma:Released
        OdmaQName qnameReleased = new OdmaQName("opendma","Released");
        try {
            OdmaProperty propReleased = obj.getProperty(qnameReleased);
            if(propReleased.getName() == null) {
                result.add("Property opendma:Released qname is null");
            }
            if(!"opendma".equals(propReleased.getName().getNamespace())) {
                result.add("Property opendma:Released qname namespace is not 'opendma', found instead'"+propReleased.getName().getNamespace()+"'");
            }
            if(!"Released".equals(propReleased.getName().getName())) {
                result.add("Property opendma:Released qname name is not 'Released', found instead'"+propReleased.getName().getName()+"'");
            }
            if(propReleased.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Released type is not 'REFERENCE'");
            }
            if(propReleased.isMultiValue() != false) {
                result.add("Property opendma:Released MultiValue is not 'false'");
            }
            if(!propReleased.isReadOnly()) {
                result.add("Property opendma:Released ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Released");
        }
        if(clazz != null && (new OdmaQName("opendma","VersionCollection")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredReleased = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameReleased.equals(pi.getQName())) {
                        if(piDeclaredReleased == null) {
                            piDeclaredReleased = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Released");
                        }
                    }
                }
            }
            if(piDeclaredReleased == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Released");
            }
            if(piDeclaredReleased != null) {
                if(!"opendma".equals(piDeclaredReleased.getNamespace())) {
                    result.add("Property info for opendma:Released in declared properties qname namespace is not 'opendma'");
                }
                if(!"Released".equals(piDeclaredReleased.getName())) {
                    result.add("Property info for opendma:Released in declared properties qname name is not 'Released'");
                }
                if(piDeclaredReleased.getDataType() != 10) {
                    result.add("Property info for opendma:Released in declared properties data type is not '10'");
                }
                if(piDeclaredReleased.isMultiValue() != false) {
                    result.add("Property info for opendma:Released in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredReleased.isReadOnly() != true) {
                    result.add("Property info for opendma:Released in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Document")).equals(piDeclaredReleased.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Released in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredReleased.isHidden() != false) {
                    result.add("Property info for opendma:Released in declared properties Hidden is not 'false'");
                }
                if(piDeclaredReleased.isRequired() != false) {
                result.add("Property info for opendma:Released in declared properties Required is not 'false'");
                }
                if(piDeclaredReleased.isSystem() != true) {
                    result.add("Property info for opendma:Released in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllReleased = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameReleased.equals(pi.getQName())) {
                    if(piAllReleased == null) {
                        piAllReleased = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Released");
                    }
                }
            }
        }
        if(piAllReleased == null) {
            result.add("All properties in class have no property info object with qname opendma:Released");
        }
        if(piAllReleased != null) {
            if(!"opendma".equals(piAllReleased.getNamespace())) {
                result.add("Property info for opendma:Released in all properties qname namespace is not 'opendma'");
            }
            if(!"Released".equals(piAllReleased.getName())) {
                result.add("Property info for opendma:Released in all properties qname name is not 'Released'");
            }
            if(piAllReleased.getDataType() != 10) {
                result.add("Property info for opendma:Released in all properties data type is not '10'");
            }
            if(piAllReleased.isMultiValue() != false) {
                result.add("Property info for opendma:Released in all properties MultiValue is not 'false'");
            }
            if(piAllReleased.isReadOnly() != true) {
                result.add("Property info for opendma:Released in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Document")).equals(piAllReleased.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Released in all properties ReadOnly is not 'true'");
            }
            if(piAllReleased.isHidden() != false) {
                result.add("Property info for opendma:Released in all properties Hidden is not 'false'");
            }
            if(piAllReleased.isRequired() != false) {
                result.add("Property info for opendma:Released in all properties Required is not 'false'");
            }
            if(piAllReleased.isSystem() != true) {
                result.add("Property info for opendma:Released in all properties System is not 'true'");
            }
        }
        // opendma:InProgress
        OdmaQName qnameInProgress = new OdmaQName("opendma","InProgress");
        try {
            OdmaProperty propInProgress = obj.getProperty(qnameInProgress);
            if(propInProgress.getName() == null) {
                result.add("Property opendma:InProgress qname is null");
            }
            if(!"opendma".equals(propInProgress.getName().getNamespace())) {
                result.add("Property opendma:InProgress qname namespace is not 'opendma', found instead'"+propInProgress.getName().getNamespace()+"'");
            }
            if(!"InProgress".equals(propInProgress.getName().getName())) {
                result.add("Property opendma:InProgress qname name is not 'InProgress', found instead'"+propInProgress.getName().getName()+"'");
            }
            if(propInProgress.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:InProgress type is not 'REFERENCE'");
            }
            if(propInProgress.isMultiValue() != false) {
                result.add("Property opendma:InProgress MultiValue is not 'false'");
            }
            if(!propInProgress.isReadOnly()) {
                result.add("Property opendma:InProgress ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:InProgress");
        }
        if(clazz != null && (new OdmaQName("opendma","VersionCollection")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredInProgress = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameInProgress.equals(pi.getQName())) {
                        if(piDeclaredInProgress == null) {
                            piDeclaredInProgress = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:InProgress");
                        }
                    }
                }
            }
            if(piDeclaredInProgress == null) {
                result.add("Declared properties in class have no property info object with qname opendma:InProgress");
            }
            if(piDeclaredInProgress != null) {
                if(!"opendma".equals(piDeclaredInProgress.getNamespace())) {
                    result.add("Property info for opendma:InProgress in declared properties qname namespace is not 'opendma'");
                }
                if(!"InProgress".equals(piDeclaredInProgress.getName())) {
                    result.add("Property info for opendma:InProgress in declared properties qname name is not 'InProgress'");
                }
                if(piDeclaredInProgress.getDataType() != 10) {
                    result.add("Property info for opendma:InProgress in declared properties data type is not '10'");
                }
                if(piDeclaredInProgress.isMultiValue() != false) {
                    result.add("Property info for opendma:InProgress in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredInProgress.isReadOnly() != true) {
                    result.add("Property info for opendma:InProgress in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Document")).equals(piDeclaredInProgress.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:InProgress in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredInProgress.isHidden() != false) {
                    result.add("Property info for opendma:InProgress in declared properties Hidden is not 'false'");
                }
                if(piDeclaredInProgress.isRequired() != false) {
                result.add("Property info for opendma:InProgress in declared properties Required is not 'false'");
                }
                if(piDeclaredInProgress.isSystem() != true) {
                    result.add("Property info for opendma:InProgress in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllInProgress = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameInProgress.equals(pi.getQName())) {
                    if(piAllInProgress == null) {
                        piAllInProgress = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:InProgress");
                    }
                }
            }
        }
        if(piAllInProgress == null) {
            result.add("All properties in class have no property info object with qname opendma:InProgress");
        }
        if(piAllInProgress != null) {
            if(!"opendma".equals(piAllInProgress.getNamespace())) {
                result.add("Property info for opendma:InProgress in all properties qname namespace is not 'opendma'");
            }
            if(!"InProgress".equals(piAllInProgress.getName())) {
                result.add("Property info for opendma:InProgress in all properties qname name is not 'InProgress'");
            }
            if(piAllInProgress.getDataType() != 10) {
                result.add("Property info for opendma:InProgress in all properties data type is not '10'");
            }
            if(piAllInProgress.isMultiValue() != false) {
                result.add("Property info for opendma:InProgress in all properties MultiValue is not 'false'");
            }
            if(piAllInProgress.isReadOnly() != true) {
                result.add("Property info for opendma:InProgress in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Document")).equals(piAllInProgress.getReferenceClass().getQName())) {
                result.add("Property info for opendma:InProgress in all properties ReadOnly is not 'true'");
            }
            if(piAllInProgress.isHidden() != false) {
                result.add("Property info for opendma:InProgress in all properties Hidden is not 'false'");
            }
            if(piAllInProgress.isRequired() != false) {
                result.add("Property info for opendma:InProgress in all properties Required is not 'false'");
            }
            if(piAllInProgress.isSystem() != true) {
                result.add("Property info for opendma:InProgress in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaContainer(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaContainer)) {
            result.add("Does not implement OdmaContainer interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Title
        OdmaQName qnameTitle = new OdmaQName("opendma","Title");
        try {
            OdmaProperty propTitle = obj.getProperty(qnameTitle);
            if(propTitle.getName() == null) {
                result.add("Property opendma:Title qname is null");
            }
            if(!"opendma".equals(propTitle.getName().getNamespace())) {
                result.add("Property opendma:Title qname namespace is not 'opendma', found instead'"+propTitle.getName().getNamespace()+"'");
            }
            if(!"Title".equals(propTitle.getName().getName())) {
                result.add("Property opendma:Title qname name is not 'Title', found instead'"+propTitle.getName().getName()+"'");
            }
            if(propTitle.getType() != OdmaType.STRING) {
                result.add("Property opendma:Title type is not 'STRING'");
            }
            if(propTitle.isMultiValue() != false) {
                result.add("Property opendma:Title MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Title");
        }
        if(clazz != null && (new OdmaQName("opendma","Container")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredTitle = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameTitle.equals(pi.getQName())) {
                        if(piDeclaredTitle == null) {
                            piDeclaredTitle = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Title");
                        }
                    }
                }
            }
            if(piDeclaredTitle == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Title");
            }
            if(piDeclaredTitle != null) {
                if(!"opendma".equals(piDeclaredTitle.getNamespace())) {
                    result.add("Property info for opendma:Title in declared properties qname namespace is not 'opendma'");
                }
                if(!"Title".equals(piDeclaredTitle.getName())) {
                    result.add("Property info for opendma:Title in declared properties qname name is not 'Title'");
                }
                if(piDeclaredTitle.getDataType() != 1) {
                    result.add("Property info for opendma:Title in declared properties data type is not '1'");
                }
                if(piDeclaredTitle.isMultiValue() != false) {
                    result.add("Property info for opendma:Title in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredTitle.isReadOnly() != false) {
                    result.add("Property info for opendma:Title in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredTitle.isHidden() != false) {
                    result.add("Property info for opendma:Title in declared properties Hidden is not 'false'");
                }
                if(piDeclaredTitle.isRequired() != false) {
                result.add("Property info for opendma:Title in declared properties Required is not 'false'");
                }
                if(piDeclaredTitle.isSystem() != true) {
                    result.add("Property info for opendma:Title in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllTitle = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameTitle.equals(pi.getQName())) {
                    if(piAllTitle == null) {
                        piAllTitle = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Title");
                    }
                }
            }
        }
        if(piAllTitle == null) {
            result.add("All properties in class have no property info object with qname opendma:Title");
        }
        if(piAllTitle != null) {
            if(!"opendma".equals(piAllTitle.getNamespace())) {
                result.add("Property info for opendma:Title in all properties qname namespace is not 'opendma'");
            }
            if(!"Title".equals(piAllTitle.getName())) {
                result.add("Property info for opendma:Title in all properties qname name is not 'Title'");
            }
            if(piAllTitle.getDataType() != 1) {
                result.add("Property info for opendma:Title in all properties data type is not '1'");
            }
            if(piAllTitle.isMultiValue() != false) {
                result.add("Property info for opendma:Title in all properties MultiValue is not 'false'");
            }
            if(piAllTitle.isReadOnly() != false) {
                result.add("Property info for opendma:Title in all properties ReadOnly is not 'false'");
            }
            if(piAllTitle.isHidden() != false) {
                result.add("Property info for opendma:Title in all properties Hidden is not 'false'");
            }
            if(piAllTitle.isRequired() != false) {
                result.add("Property info for opendma:Title in all properties Required is not 'false'");
            }
            if(piAllTitle.isSystem() != true) {
                result.add("Property info for opendma:Title in all properties System is not 'true'");
            }
        }
        // opendma:Containees
        OdmaQName qnameContainees = new OdmaQName("opendma","Containees");
        try {
            OdmaProperty propContainees = obj.getProperty(qnameContainees);
            if(propContainees.getName() == null) {
                result.add("Property opendma:Containees qname is null");
            }
            if(!"opendma".equals(propContainees.getName().getNamespace())) {
                result.add("Property opendma:Containees qname namespace is not 'opendma', found instead'"+propContainees.getName().getNamespace()+"'");
            }
            if(!"Containees".equals(propContainees.getName().getName())) {
                result.add("Property opendma:Containees qname name is not 'Containees', found instead'"+propContainees.getName().getName()+"'");
            }
            if(propContainees.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Containees type is not 'REFERENCE'");
            }
            if(propContainees.isMultiValue() != true) {
                result.add("Property opendma:Containees MultiValue is not 'true'");
            }
            if(!propContainees.isReadOnly()) {
                result.add("Property opendma:Containees ReadOnly must be 'true'");
            }
            if(propContainees.getValue() == null) {
                result.add("Property opendma:Containees is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Containees");
        }
        if(clazz != null && (new OdmaQName("opendma","Container")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContainees = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContainees.equals(pi.getQName())) {
                        if(piDeclaredContainees == null) {
                            piDeclaredContainees = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Containees");
                        }
                    }
                }
            }
            if(piDeclaredContainees == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Containees");
            }
            if(piDeclaredContainees != null) {
                if(!"opendma".equals(piDeclaredContainees.getNamespace())) {
                    result.add("Property info for opendma:Containees in declared properties qname namespace is not 'opendma'");
                }
                if(!"Containees".equals(piDeclaredContainees.getName())) {
                    result.add("Property info for opendma:Containees in declared properties qname name is not 'Containees'");
                }
                if(piDeclaredContainees.getDataType() != 10) {
                    result.add("Property info for opendma:Containees in declared properties data type is not '10'");
                }
                if(piDeclaredContainees.isMultiValue() != true) {
                    result.add("Property info for opendma:Containees in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredContainees.isReadOnly() != true) {
                    result.add("Property info for opendma:Containees in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Containable")).equals(piDeclaredContainees.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Containees in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredContainees.isHidden() != false) {
                    result.add("Property info for opendma:Containees in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContainees.isRequired() != false) {
                result.add("Property info for opendma:Containees in declared properties Required is not 'false'");
                }
                if(piDeclaredContainees.isSystem() != true) {
                    result.add("Property info for opendma:Containees in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContainees = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContainees.equals(pi.getQName())) {
                    if(piAllContainees == null) {
                        piAllContainees = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Containees");
                    }
                }
            }
        }
        if(piAllContainees == null) {
            result.add("All properties in class have no property info object with qname opendma:Containees");
        }
        if(piAllContainees != null) {
            if(!"opendma".equals(piAllContainees.getNamespace())) {
                result.add("Property info for opendma:Containees in all properties qname namespace is not 'opendma'");
            }
            if(!"Containees".equals(piAllContainees.getName())) {
                result.add("Property info for opendma:Containees in all properties qname name is not 'Containees'");
            }
            if(piAllContainees.getDataType() != 10) {
                result.add("Property info for opendma:Containees in all properties data type is not '10'");
            }
            if(piAllContainees.isMultiValue() != true) {
                result.add("Property info for opendma:Containees in all properties MultiValue is not 'true'");
            }
            if(piAllContainees.isReadOnly() != true) {
                result.add("Property info for opendma:Containees in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Containable")).equals(piAllContainees.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Containees in all properties ReadOnly is not 'true'");
            }
            if(piAllContainees.isHidden() != false) {
                result.add("Property info for opendma:Containees in all properties Hidden is not 'false'");
            }
            if(piAllContainees.isRequired() != false) {
                result.add("Property info for opendma:Containees in all properties Required is not 'false'");
            }
            if(piAllContainees.isSystem() != true) {
                result.add("Property info for opendma:Containees in all properties System is not 'true'");
            }
        }
        // opendma:Associations
        OdmaQName qnameAssociations = new OdmaQName("opendma","Associations");
        try {
            OdmaProperty propAssociations = obj.getProperty(qnameAssociations);
            if(propAssociations.getName() == null) {
                result.add("Property opendma:Associations qname is null");
            }
            if(!"opendma".equals(propAssociations.getName().getNamespace())) {
                result.add("Property opendma:Associations qname namespace is not 'opendma', found instead'"+propAssociations.getName().getNamespace()+"'");
            }
            if(!"Associations".equals(propAssociations.getName().getName())) {
                result.add("Property opendma:Associations qname name is not 'Associations', found instead'"+propAssociations.getName().getName()+"'");
            }
            if(propAssociations.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Associations type is not 'REFERENCE'");
            }
            if(propAssociations.isMultiValue() != true) {
                result.add("Property opendma:Associations MultiValue is not 'true'");
            }
            if(!propAssociations.isReadOnly()) {
                result.add("Property opendma:Associations ReadOnly must be 'true'");
            }
            if(propAssociations.getValue() == null) {
                result.add("Property opendma:Associations is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Associations");
        }
        if(clazz != null && (new OdmaQName("opendma","Container")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredAssociations = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameAssociations.equals(pi.getQName())) {
                        if(piDeclaredAssociations == null) {
                            piDeclaredAssociations = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Associations");
                        }
                    }
                }
            }
            if(piDeclaredAssociations == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Associations");
            }
            if(piDeclaredAssociations != null) {
                if(!"opendma".equals(piDeclaredAssociations.getNamespace())) {
                    result.add("Property info for opendma:Associations in declared properties qname namespace is not 'opendma'");
                }
                if(!"Associations".equals(piDeclaredAssociations.getName())) {
                    result.add("Property info for opendma:Associations in declared properties qname name is not 'Associations'");
                }
                if(piDeclaredAssociations.getDataType() != 10) {
                    result.add("Property info for opendma:Associations in declared properties data type is not '10'");
                }
                if(piDeclaredAssociations.isMultiValue() != true) {
                    result.add("Property info for opendma:Associations in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredAssociations.isReadOnly() != true) {
                    result.add("Property info for opendma:Associations in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Association")).equals(piDeclaredAssociations.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Associations in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredAssociations.isHidden() != false) {
                    result.add("Property info for opendma:Associations in declared properties Hidden is not 'false'");
                }
                if(piDeclaredAssociations.isRequired() != false) {
                result.add("Property info for opendma:Associations in declared properties Required is not 'false'");
                }
                if(piDeclaredAssociations.isSystem() != true) {
                    result.add("Property info for opendma:Associations in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllAssociations = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameAssociations.equals(pi.getQName())) {
                    if(piAllAssociations == null) {
                        piAllAssociations = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Associations");
                    }
                }
            }
        }
        if(piAllAssociations == null) {
            result.add("All properties in class have no property info object with qname opendma:Associations");
        }
        if(piAllAssociations != null) {
            if(!"opendma".equals(piAllAssociations.getNamespace())) {
                result.add("Property info for opendma:Associations in all properties qname namespace is not 'opendma'");
            }
            if(!"Associations".equals(piAllAssociations.getName())) {
                result.add("Property info for opendma:Associations in all properties qname name is not 'Associations'");
            }
            if(piAllAssociations.getDataType() != 10) {
                result.add("Property info for opendma:Associations in all properties data type is not '10'");
            }
            if(piAllAssociations.isMultiValue() != true) {
                result.add("Property info for opendma:Associations in all properties MultiValue is not 'true'");
            }
            if(piAllAssociations.isReadOnly() != true) {
                result.add("Property info for opendma:Associations in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Association")).equals(piAllAssociations.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Associations in all properties ReadOnly is not 'true'");
            }
            if(piAllAssociations.isHidden() != false) {
                result.add("Property info for opendma:Associations in all properties Hidden is not 'false'");
            }
            if(piAllAssociations.isRequired() != false) {
                result.add("Property info for opendma:Associations in all properties Required is not 'false'");
            }
            if(piAllAssociations.isSystem() != true) {
                result.add("Property info for opendma:Associations in all properties System is not 'true'");
            }
        }
        // opendma:CreatedAt
        OdmaQName qnameCreatedAt = new OdmaQName("opendma","CreatedAt");
        try {
            OdmaProperty propCreatedAt = obj.getProperty(qnameCreatedAt);
            if(propCreatedAt.getName() == null) {
                result.add("Property opendma:CreatedAt qname is null");
            }
            if(!"opendma".equals(propCreatedAt.getName().getNamespace())) {
                result.add("Property opendma:CreatedAt qname namespace is not 'opendma', found instead'"+propCreatedAt.getName().getNamespace()+"'");
            }
            if(!"CreatedAt".equals(propCreatedAt.getName().getName())) {
                result.add("Property opendma:CreatedAt qname name is not 'CreatedAt', found instead'"+propCreatedAt.getName().getName()+"'");
            }
            if(propCreatedAt.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:CreatedAt type is not 'DATETIME'");
            }
            if(propCreatedAt.isMultiValue() != false) {
                result.add("Property opendma:CreatedAt MultiValue is not 'false'");
            }
            if(!propCreatedAt.isReadOnly()) {
                result.add("Property opendma:CreatedAt ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CreatedAt");
        }
        if(clazz != null && (new OdmaQName("opendma","Container")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCreatedAt = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCreatedAt.equals(pi.getQName())) {
                        if(piDeclaredCreatedAt == null) {
                            piDeclaredCreatedAt = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CreatedAt");
                        }
                    }
                }
            }
            if(piDeclaredCreatedAt == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CreatedAt");
            }
            if(piDeclaredCreatedAt != null) {
                if(!"opendma".equals(piDeclaredCreatedAt.getNamespace())) {
                    result.add("Property info for opendma:CreatedAt in declared properties qname namespace is not 'opendma'");
                }
                if(!"CreatedAt".equals(piDeclaredCreatedAt.getName())) {
                    result.add("Property info for opendma:CreatedAt in declared properties qname name is not 'CreatedAt'");
                }
                if(piDeclaredCreatedAt.getDataType() != 8) {
                    result.add("Property info for opendma:CreatedAt in declared properties data type is not '8'");
                }
                if(piDeclaredCreatedAt.isMultiValue() != false) {
                    result.add("Property info for opendma:CreatedAt in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCreatedAt.isReadOnly() != true) {
                    result.add("Property info for opendma:CreatedAt in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCreatedAt.isHidden() != false) {
                    result.add("Property info for opendma:CreatedAt in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCreatedAt.isRequired() != false) {
                result.add("Property info for opendma:CreatedAt in declared properties Required is not 'false'");
                }
                if(piDeclaredCreatedAt.isSystem() != true) {
                    result.add("Property info for opendma:CreatedAt in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCreatedAt = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCreatedAt.equals(pi.getQName())) {
                    if(piAllCreatedAt == null) {
                        piAllCreatedAt = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CreatedAt");
                    }
                }
            }
        }
        if(piAllCreatedAt == null) {
            result.add("All properties in class have no property info object with qname opendma:CreatedAt");
        }
        if(piAllCreatedAt != null) {
            if(!"opendma".equals(piAllCreatedAt.getNamespace())) {
                result.add("Property info for opendma:CreatedAt in all properties qname namespace is not 'opendma'");
            }
            if(!"CreatedAt".equals(piAllCreatedAt.getName())) {
                result.add("Property info for opendma:CreatedAt in all properties qname name is not 'CreatedAt'");
            }
            if(piAllCreatedAt.getDataType() != 8) {
                result.add("Property info for opendma:CreatedAt in all properties data type is not '8'");
            }
            if(piAllCreatedAt.isMultiValue() != false) {
                result.add("Property info for opendma:CreatedAt in all properties MultiValue is not 'false'");
            }
            if(piAllCreatedAt.isReadOnly() != true) {
                result.add("Property info for opendma:CreatedAt in all properties ReadOnly is not 'true'");
            }
            if(piAllCreatedAt.isHidden() != false) {
                result.add("Property info for opendma:CreatedAt in all properties Hidden is not 'false'");
            }
            if(piAllCreatedAt.isRequired() != false) {
                result.add("Property info for opendma:CreatedAt in all properties Required is not 'false'");
            }
            if(piAllCreatedAt.isSystem() != true) {
                result.add("Property info for opendma:CreatedAt in all properties System is not 'true'");
            }
        }
        // opendma:CreatedBy
        OdmaQName qnameCreatedBy = new OdmaQName("opendma","CreatedBy");
        try {
            OdmaProperty propCreatedBy = obj.getProperty(qnameCreatedBy);
            if(propCreatedBy.getName() == null) {
                result.add("Property opendma:CreatedBy qname is null");
            }
            if(!"opendma".equals(propCreatedBy.getName().getNamespace())) {
                result.add("Property opendma:CreatedBy qname namespace is not 'opendma', found instead'"+propCreatedBy.getName().getNamespace()+"'");
            }
            if(!"CreatedBy".equals(propCreatedBy.getName().getName())) {
                result.add("Property opendma:CreatedBy qname name is not 'CreatedBy', found instead'"+propCreatedBy.getName().getName()+"'");
            }
            if(propCreatedBy.getType() != OdmaType.STRING) {
                result.add("Property opendma:CreatedBy type is not 'STRING'");
            }
            if(propCreatedBy.isMultiValue() != false) {
                result.add("Property opendma:CreatedBy MultiValue is not 'false'");
            }
            if(!propCreatedBy.isReadOnly()) {
                result.add("Property opendma:CreatedBy ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CreatedBy");
        }
        if(clazz != null && (new OdmaQName("opendma","Container")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCreatedBy = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCreatedBy.equals(pi.getQName())) {
                        if(piDeclaredCreatedBy == null) {
                            piDeclaredCreatedBy = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CreatedBy");
                        }
                    }
                }
            }
            if(piDeclaredCreatedBy == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CreatedBy");
            }
            if(piDeclaredCreatedBy != null) {
                if(!"opendma".equals(piDeclaredCreatedBy.getNamespace())) {
                    result.add("Property info for opendma:CreatedBy in declared properties qname namespace is not 'opendma'");
                }
                if(!"CreatedBy".equals(piDeclaredCreatedBy.getName())) {
                    result.add("Property info for opendma:CreatedBy in declared properties qname name is not 'CreatedBy'");
                }
                if(piDeclaredCreatedBy.getDataType() != 1) {
                    result.add("Property info for opendma:CreatedBy in declared properties data type is not '1'");
                }
                if(piDeclaredCreatedBy.isMultiValue() != false) {
                    result.add("Property info for opendma:CreatedBy in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCreatedBy.isReadOnly() != true) {
                    result.add("Property info for opendma:CreatedBy in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCreatedBy.isHidden() != false) {
                    result.add("Property info for opendma:CreatedBy in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCreatedBy.isRequired() != false) {
                result.add("Property info for opendma:CreatedBy in declared properties Required is not 'false'");
                }
                if(piDeclaredCreatedBy.isSystem() != true) {
                    result.add("Property info for opendma:CreatedBy in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCreatedBy = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCreatedBy.equals(pi.getQName())) {
                    if(piAllCreatedBy == null) {
                        piAllCreatedBy = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CreatedBy");
                    }
                }
            }
        }
        if(piAllCreatedBy == null) {
            result.add("All properties in class have no property info object with qname opendma:CreatedBy");
        }
        if(piAllCreatedBy != null) {
            if(!"opendma".equals(piAllCreatedBy.getNamespace())) {
                result.add("Property info for opendma:CreatedBy in all properties qname namespace is not 'opendma'");
            }
            if(!"CreatedBy".equals(piAllCreatedBy.getName())) {
                result.add("Property info for opendma:CreatedBy in all properties qname name is not 'CreatedBy'");
            }
            if(piAllCreatedBy.getDataType() != 1) {
                result.add("Property info for opendma:CreatedBy in all properties data type is not '1'");
            }
            if(piAllCreatedBy.isMultiValue() != false) {
                result.add("Property info for opendma:CreatedBy in all properties MultiValue is not 'false'");
            }
            if(piAllCreatedBy.isReadOnly() != true) {
                result.add("Property info for opendma:CreatedBy in all properties ReadOnly is not 'true'");
            }
            if(piAllCreatedBy.isHidden() != false) {
                result.add("Property info for opendma:CreatedBy in all properties Hidden is not 'false'");
            }
            if(piAllCreatedBy.isRequired() != false) {
                result.add("Property info for opendma:CreatedBy in all properties Required is not 'false'");
            }
            if(piAllCreatedBy.isSystem() != true) {
                result.add("Property info for opendma:CreatedBy in all properties System is not 'true'");
            }
        }
        // opendma:LastModifiedAt
        OdmaQName qnameLastModifiedAt = new OdmaQName("opendma","LastModifiedAt");
        try {
            OdmaProperty propLastModifiedAt = obj.getProperty(qnameLastModifiedAt);
            if(propLastModifiedAt.getName() == null) {
                result.add("Property opendma:LastModifiedAt qname is null");
            }
            if(!"opendma".equals(propLastModifiedAt.getName().getNamespace())) {
                result.add("Property opendma:LastModifiedAt qname namespace is not 'opendma', found instead'"+propLastModifiedAt.getName().getNamespace()+"'");
            }
            if(!"LastModifiedAt".equals(propLastModifiedAt.getName().getName())) {
                result.add("Property opendma:LastModifiedAt qname name is not 'LastModifiedAt', found instead'"+propLastModifiedAt.getName().getName()+"'");
            }
            if(propLastModifiedAt.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:LastModifiedAt type is not 'DATETIME'");
            }
            if(propLastModifiedAt.isMultiValue() != false) {
                result.add("Property opendma:LastModifiedAt MultiValue is not 'false'");
            }
            if(!propLastModifiedAt.isReadOnly()) {
                result.add("Property opendma:LastModifiedAt ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:LastModifiedAt");
        }
        if(clazz != null && (new OdmaQName("opendma","Container")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLastModifiedAt = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLastModifiedAt.equals(pi.getQName())) {
                        if(piDeclaredLastModifiedAt == null) {
                            piDeclaredLastModifiedAt = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:LastModifiedAt");
                        }
                    }
                }
            }
            if(piDeclaredLastModifiedAt == null) {
                result.add("Declared properties in class have no property info object with qname opendma:LastModifiedAt");
            }
            if(piDeclaredLastModifiedAt != null) {
                if(!"opendma".equals(piDeclaredLastModifiedAt.getNamespace())) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties qname namespace is not 'opendma'");
                }
                if(!"LastModifiedAt".equals(piDeclaredLastModifiedAt.getName())) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties qname name is not 'LastModifiedAt'");
                }
                if(piDeclaredLastModifiedAt.getDataType() != 8) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties data type is not '8'");
                }
                if(piDeclaredLastModifiedAt.isMultiValue() != false) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isReadOnly() != true) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredLastModifiedAt.isHidden() != false) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedAt in declared properties Required is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isSystem() != true) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLastModifiedAt = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLastModifiedAt.equals(pi.getQName())) {
                    if(piAllLastModifiedAt == null) {
                        piAllLastModifiedAt = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:LastModifiedAt");
                    }
                }
            }
        }
        if(piAllLastModifiedAt == null) {
            result.add("All properties in class have no property info object with qname opendma:LastModifiedAt");
        }
        if(piAllLastModifiedAt != null) {
            if(!"opendma".equals(piAllLastModifiedAt.getNamespace())) {
                result.add("Property info for opendma:LastModifiedAt in all properties qname namespace is not 'opendma'");
            }
            if(!"LastModifiedAt".equals(piAllLastModifiedAt.getName())) {
                result.add("Property info for opendma:LastModifiedAt in all properties qname name is not 'LastModifiedAt'");
            }
            if(piAllLastModifiedAt.getDataType() != 8) {
                result.add("Property info for opendma:LastModifiedAt in all properties data type is not '8'");
            }
            if(piAllLastModifiedAt.isMultiValue() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties MultiValue is not 'false'");
            }
            if(piAllLastModifiedAt.isReadOnly() != true) {
                result.add("Property info for opendma:LastModifiedAt in all properties ReadOnly is not 'true'");
            }
            if(piAllLastModifiedAt.isHidden() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties Hidden is not 'false'");
            }
            if(piAllLastModifiedAt.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties Required is not 'false'");
            }
            if(piAllLastModifiedAt.isSystem() != true) {
                result.add("Property info for opendma:LastModifiedAt in all properties System is not 'true'");
            }
        }
        // opendma:LastModifiedBy
        OdmaQName qnameLastModifiedBy = new OdmaQName("opendma","LastModifiedBy");
        try {
            OdmaProperty propLastModifiedBy = obj.getProperty(qnameLastModifiedBy);
            if(propLastModifiedBy.getName() == null) {
                result.add("Property opendma:LastModifiedBy qname is null");
            }
            if(!"opendma".equals(propLastModifiedBy.getName().getNamespace())) {
                result.add("Property opendma:LastModifiedBy qname namespace is not 'opendma', found instead'"+propLastModifiedBy.getName().getNamespace()+"'");
            }
            if(!"LastModifiedBy".equals(propLastModifiedBy.getName().getName())) {
                result.add("Property opendma:LastModifiedBy qname name is not 'LastModifiedBy', found instead'"+propLastModifiedBy.getName().getName()+"'");
            }
            if(propLastModifiedBy.getType() != OdmaType.STRING) {
                result.add("Property opendma:LastModifiedBy type is not 'STRING'");
            }
            if(propLastModifiedBy.isMultiValue() != false) {
                result.add("Property opendma:LastModifiedBy MultiValue is not 'false'");
            }
            if(!propLastModifiedBy.isReadOnly()) {
                result.add("Property opendma:LastModifiedBy ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:LastModifiedBy");
        }
        if(clazz != null && (new OdmaQName("opendma","Container")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLastModifiedBy = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLastModifiedBy.equals(pi.getQName())) {
                        if(piDeclaredLastModifiedBy == null) {
                            piDeclaredLastModifiedBy = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:LastModifiedBy");
                        }
                    }
                }
            }
            if(piDeclaredLastModifiedBy == null) {
                result.add("Declared properties in class have no property info object with qname opendma:LastModifiedBy");
            }
            if(piDeclaredLastModifiedBy != null) {
                if(!"opendma".equals(piDeclaredLastModifiedBy.getNamespace())) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties qname namespace is not 'opendma'");
                }
                if(!"LastModifiedBy".equals(piDeclaredLastModifiedBy.getName())) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties qname name is not 'LastModifiedBy'");
                }
                if(piDeclaredLastModifiedBy.getDataType() != 1) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties data type is not '1'");
                }
                if(piDeclaredLastModifiedBy.isMultiValue() != false) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isReadOnly() != true) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredLastModifiedBy.isHidden() != false) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedBy in declared properties Required is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isSystem() != true) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLastModifiedBy = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLastModifiedBy.equals(pi.getQName())) {
                    if(piAllLastModifiedBy == null) {
                        piAllLastModifiedBy = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:LastModifiedBy");
                    }
                }
            }
        }
        if(piAllLastModifiedBy == null) {
            result.add("All properties in class have no property info object with qname opendma:LastModifiedBy");
        }
        if(piAllLastModifiedBy != null) {
            if(!"opendma".equals(piAllLastModifiedBy.getNamespace())) {
                result.add("Property info for opendma:LastModifiedBy in all properties qname namespace is not 'opendma'");
            }
            if(!"LastModifiedBy".equals(piAllLastModifiedBy.getName())) {
                result.add("Property info for opendma:LastModifiedBy in all properties qname name is not 'LastModifiedBy'");
            }
            if(piAllLastModifiedBy.getDataType() != 1) {
                result.add("Property info for opendma:LastModifiedBy in all properties data type is not '1'");
            }
            if(piAllLastModifiedBy.isMultiValue() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties MultiValue is not 'false'");
            }
            if(piAllLastModifiedBy.isReadOnly() != true) {
                result.add("Property info for opendma:LastModifiedBy in all properties ReadOnly is not 'true'");
            }
            if(piAllLastModifiedBy.isHidden() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties Hidden is not 'false'");
            }
            if(piAllLastModifiedBy.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties Required is not 'false'");
            }
            if(piAllLastModifiedBy.isSystem() != true) {
                result.add("Property info for opendma:LastModifiedBy in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaFolder(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaFolder)) {
            result.add("Does not implement OdmaFolder interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaContainer(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Parent
        OdmaQName qnameParent = new OdmaQName("opendma","Parent");
        try {
            OdmaProperty propParent = obj.getProperty(qnameParent);
            if(propParent.getName() == null) {
                result.add("Property opendma:Parent qname is null");
            }
            if(!"opendma".equals(propParent.getName().getNamespace())) {
                result.add("Property opendma:Parent qname namespace is not 'opendma', found instead'"+propParent.getName().getNamespace()+"'");
            }
            if(!"Parent".equals(propParent.getName().getName())) {
                result.add("Property opendma:Parent qname name is not 'Parent', found instead'"+propParent.getName().getName()+"'");
            }
            if(propParent.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Parent type is not 'REFERENCE'");
            }
            if(propParent.isMultiValue() != false) {
                result.add("Property opendma:Parent MultiValue is not 'false'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Parent");
        }
        if(clazz != null && (new OdmaQName("opendma","Folder")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredParent = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameParent.equals(pi.getQName())) {
                        if(piDeclaredParent == null) {
                            piDeclaredParent = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Parent");
                        }
                    }
                }
            }
            if(piDeclaredParent == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Parent");
            }
            if(piDeclaredParent != null) {
                if(!"opendma".equals(piDeclaredParent.getNamespace())) {
                    result.add("Property info for opendma:Parent in declared properties qname namespace is not 'opendma'");
                }
                if(!"Parent".equals(piDeclaredParent.getName())) {
                    result.add("Property info for opendma:Parent in declared properties qname name is not 'Parent'");
                }
                if(piDeclaredParent.getDataType() != 10) {
                    result.add("Property info for opendma:Parent in declared properties data type is not '10'");
                }
                if(piDeclaredParent.isMultiValue() != false) {
                    result.add("Property info for opendma:Parent in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredParent.isReadOnly() != false) {
                    result.add("Property info for opendma:Parent in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","Folder")).equals(piDeclaredParent.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Parent in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredParent.isHidden() != false) {
                    result.add("Property info for opendma:Parent in declared properties Hidden is not 'false'");
                }
                if(piDeclaredParent.isRequired() != false) {
                result.add("Property info for opendma:Parent in declared properties Required is not 'false'");
                }
                if(piDeclaredParent.isSystem() != true) {
                    result.add("Property info for opendma:Parent in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllParent = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameParent.equals(pi.getQName())) {
                    if(piAllParent == null) {
                        piAllParent = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Parent");
                    }
                }
            }
        }
        if(piAllParent == null) {
            result.add("All properties in class have no property info object with qname opendma:Parent");
        }
        if(piAllParent != null) {
            if(!"opendma".equals(piAllParent.getNamespace())) {
                result.add("Property info for opendma:Parent in all properties qname namespace is not 'opendma'");
            }
            if(!"Parent".equals(piAllParent.getName())) {
                result.add("Property info for opendma:Parent in all properties qname name is not 'Parent'");
            }
            if(piAllParent.getDataType() != 10) {
                result.add("Property info for opendma:Parent in all properties data type is not '10'");
            }
            if(piAllParent.isMultiValue() != false) {
                result.add("Property info for opendma:Parent in all properties MultiValue is not 'false'");
            }
            if(piAllParent.isReadOnly() != false) {
                result.add("Property info for opendma:Parent in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","Folder")).equals(piAllParent.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Parent in all properties ReadOnly is not 'false'");
            }
            if(piAllParent.isHidden() != false) {
                result.add("Property info for opendma:Parent in all properties Hidden is not 'false'");
            }
            if(piAllParent.isRequired() != false) {
                result.add("Property info for opendma:Parent in all properties Required is not 'false'");
            }
            if(piAllParent.isSystem() != true) {
                result.add("Property info for opendma:Parent in all properties System is not 'true'");
            }
        }
        // opendma:SubFolders
        OdmaQName qnameSubFolders = new OdmaQName("opendma","SubFolders");
        try {
            OdmaProperty propSubFolders = obj.getProperty(qnameSubFolders);
            if(propSubFolders.getName() == null) {
                result.add("Property opendma:SubFolders qname is null");
            }
            if(!"opendma".equals(propSubFolders.getName().getNamespace())) {
                result.add("Property opendma:SubFolders qname namespace is not 'opendma', found instead'"+propSubFolders.getName().getNamespace()+"'");
            }
            if(!"SubFolders".equals(propSubFolders.getName().getName())) {
                result.add("Property opendma:SubFolders qname name is not 'SubFolders', found instead'"+propSubFolders.getName().getName()+"'");
            }
            if(propSubFolders.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:SubFolders type is not 'REFERENCE'");
            }
            if(propSubFolders.isMultiValue() != true) {
                result.add("Property opendma:SubFolders MultiValue is not 'true'");
            }
            if(!propSubFolders.isReadOnly()) {
                result.add("Property opendma:SubFolders ReadOnly must be 'true'");
            }
            if(propSubFolders.getValue() == null) {
                result.add("Property opendma:SubFolders is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:SubFolders");
        }
        if(clazz != null && (new OdmaQName("opendma","Folder")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredSubFolders = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameSubFolders.equals(pi.getQName())) {
                        if(piDeclaredSubFolders == null) {
                            piDeclaredSubFolders = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:SubFolders");
                        }
                    }
                }
            }
            if(piDeclaredSubFolders == null) {
                result.add("Declared properties in class have no property info object with qname opendma:SubFolders");
            }
            if(piDeclaredSubFolders != null) {
                if(!"opendma".equals(piDeclaredSubFolders.getNamespace())) {
                    result.add("Property info for opendma:SubFolders in declared properties qname namespace is not 'opendma'");
                }
                if(!"SubFolders".equals(piDeclaredSubFolders.getName())) {
                    result.add("Property info for opendma:SubFolders in declared properties qname name is not 'SubFolders'");
                }
                if(piDeclaredSubFolders.getDataType() != 10) {
                    result.add("Property info for opendma:SubFolders in declared properties data type is not '10'");
                }
                if(piDeclaredSubFolders.isMultiValue() != true) {
                    result.add("Property info for opendma:SubFolders in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredSubFolders.isReadOnly() != true) {
                    result.add("Property info for opendma:SubFolders in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Folder")).equals(piDeclaredSubFolders.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:SubFolders in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredSubFolders.isHidden() != false) {
                    result.add("Property info for opendma:SubFolders in declared properties Hidden is not 'false'");
                }
                if(piDeclaredSubFolders.isRequired() != false) {
                result.add("Property info for opendma:SubFolders in declared properties Required is not 'false'");
                }
                if(piDeclaredSubFolders.isSystem() != true) {
                    result.add("Property info for opendma:SubFolders in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllSubFolders = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameSubFolders.equals(pi.getQName())) {
                    if(piAllSubFolders == null) {
                        piAllSubFolders = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:SubFolders");
                    }
                }
            }
        }
        if(piAllSubFolders == null) {
            result.add("All properties in class have no property info object with qname opendma:SubFolders");
        }
        if(piAllSubFolders != null) {
            if(!"opendma".equals(piAllSubFolders.getNamespace())) {
                result.add("Property info for opendma:SubFolders in all properties qname namespace is not 'opendma'");
            }
            if(!"SubFolders".equals(piAllSubFolders.getName())) {
                result.add("Property info for opendma:SubFolders in all properties qname name is not 'SubFolders'");
            }
            if(piAllSubFolders.getDataType() != 10) {
                result.add("Property info for opendma:SubFolders in all properties data type is not '10'");
            }
            if(piAllSubFolders.isMultiValue() != true) {
                result.add("Property info for opendma:SubFolders in all properties MultiValue is not 'true'");
            }
            if(piAllSubFolders.isReadOnly() != true) {
                result.add("Property info for opendma:SubFolders in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Folder")).equals(piAllSubFolders.getReferenceClass().getQName())) {
                result.add("Property info for opendma:SubFolders in all properties ReadOnly is not 'true'");
            }
            if(piAllSubFolders.isHidden() != false) {
                result.add("Property info for opendma:SubFolders in all properties Hidden is not 'false'");
            }
            if(piAllSubFolders.isRequired() != false) {
                result.add("Property info for opendma:SubFolders in all properties Required is not 'false'");
            }
            if(piAllSubFolders.isSystem() != true) {
                result.add("Property info for opendma:SubFolders in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaContainable(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaContainable)) {
            result.add("Does not implement OdmaContainable interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:ContainedIn
        OdmaQName qnameContainedIn = new OdmaQName("opendma","ContainedIn");
        try {
            OdmaProperty propContainedIn = obj.getProperty(qnameContainedIn);
            if(propContainedIn.getName() == null) {
                result.add("Property opendma:ContainedIn qname is null");
            }
            if(!"opendma".equals(propContainedIn.getName().getNamespace())) {
                result.add("Property opendma:ContainedIn qname namespace is not 'opendma', found instead'"+propContainedIn.getName().getNamespace()+"'");
            }
            if(!"ContainedIn".equals(propContainedIn.getName().getName())) {
                result.add("Property opendma:ContainedIn qname name is not 'ContainedIn', found instead'"+propContainedIn.getName().getName()+"'");
            }
            if(propContainedIn.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:ContainedIn type is not 'REFERENCE'");
            }
            if(propContainedIn.isMultiValue() != true) {
                result.add("Property opendma:ContainedIn MultiValue is not 'true'");
            }
            if(!propContainedIn.isReadOnly()) {
                result.add("Property opendma:ContainedIn ReadOnly must be 'true'");
            }
            if(propContainedIn.getValue() == null) {
                result.add("Property opendma:ContainedIn is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ContainedIn");
        }
        if(clazz != null && (new OdmaQName("opendma","Containable")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContainedIn = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContainedIn.equals(pi.getQName())) {
                        if(piDeclaredContainedIn == null) {
                            piDeclaredContainedIn = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ContainedIn");
                        }
                    }
                }
            }
            if(piDeclaredContainedIn == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ContainedIn");
            }
            if(piDeclaredContainedIn != null) {
                if(!"opendma".equals(piDeclaredContainedIn.getNamespace())) {
                    result.add("Property info for opendma:ContainedIn in declared properties qname namespace is not 'opendma'");
                }
                if(!"ContainedIn".equals(piDeclaredContainedIn.getName())) {
                    result.add("Property info for opendma:ContainedIn in declared properties qname name is not 'ContainedIn'");
                }
                if(piDeclaredContainedIn.getDataType() != 10) {
                    result.add("Property info for opendma:ContainedIn in declared properties data type is not '10'");
                }
                if(piDeclaredContainedIn.isMultiValue() != true) {
                    result.add("Property info for opendma:ContainedIn in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredContainedIn.isReadOnly() != true) {
                    result.add("Property info for opendma:ContainedIn in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Container")).equals(piDeclaredContainedIn.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:ContainedIn in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredContainedIn.isHidden() != false) {
                    result.add("Property info for opendma:ContainedIn in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContainedIn.isRequired() != false) {
                result.add("Property info for opendma:ContainedIn in declared properties Required is not 'false'");
                }
                if(piDeclaredContainedIn.isSystem() != true) {
                    result.add("Property info for opendma:ContainedIn in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContainedIn = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContainedIn.equals(pi.getQName())) {
                    if(piAllContainedIn == null) {
                        piAllContainedIn = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ContainedIn");
                    }
                }
            }
        }
        if(piAllContainedIn == null) {
            result.add("All properties in class have no property info object with qname opendma:ContainedIn");
        }
        if(piAllContainedIn != null) {
            if(!"opendma".equals(piAllContainedIn.getNamespace())) {
                result.add("Property info for opendma:ContainedIn in all properties qname namespace is not 'opendma'");
            }
            if(!"ContainedIn".equals(piAllContainedIn.getName())) {
                result.add("Property info for opendma:ContainedIn in all properties qname name is not 'ContainedIn'");
            }
            if(piAllContainedIn.getDataType() != 10) {
                result.add("Property info for opendma:ContainedIn in all properties data type is not '10'");
            }
            if(piAllContainedIn.isMultiValue() != true) {
                result.add("Property info for opendma:ContainedIn in all properties MultiValue is not 'true'");
            }
            if(piAllContainedIn.isReadOnly() != true) {
                result.add("Property info for opendma:ContainedIn in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Container")).equals(piAllContainedIn.getReferenceClass().getQName())) {
                result.add("Property info for opendma:ContainedIn in all properties ReadOnly is not 'true'");
            }
            if(piAllContainedIn.isHidden() != false) {
                result.add("Property info for opendma:ContainedIn in all properties Hidden is not 'false'");
            }
            if(piAllContainedIn.isRequired() != false) {
                result.add("Property info for opendma:ContainedIn in all properties Required is not 'false'");
            }
            if(piAllContainedIn.isSystem() != true) {
                result.add("Property info for opendma:ContainedIn in all properties System is not 'true'");
            }
        }
        // opendma:ContainedInAssociations
        OdmaQName qnameContainedInAssociations = new OdmaQName("opendma","ContainedInAssociations");
        try {
            OdmaProperty propContainedInAssociations = obj.getProperty(qnameContainedInAssociations);
            if(propContainedInAssociations.getName() == null) {
                result.add("Property opendma:ContainedInAssociations qname is null");
            }
            if(!"opendma".equals(propContainedInAssociations.getName().getNamespace())) {
                result.add("Property opendma:ContainedInAssociations qname namespace is not 'opendma', found instead'"+propContainedInAssociations.getName().getNamespace()+"'");
            }
            if(!"ContainedInAssociations".equals(propContainedInAssociations.getName().getName())) {
                result.add("Property opendma:ContainedInAssociations qname name is not 'ContainedInAssociations', found instead'"+propContainedInAssociations.getName().getName()+"'");
            }
            if(propContainedInAssociations.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:ContainedInAssociations type is not 'REFERENCE'");
            }
            if(propContainedInAssociations.isMultiValue() != true) {
                result.add("Property opendma:ContainedInAssociations MultiValue is not 'true'");
            }
            if(!propContainedInAssociations.isReadOnly()) {
                result.add("Property opendma:ContainedInAssociations ReadOnly must be 'true'");
            }
            if(propContainedInAssociations.getValue() == null) {
                result.add("Property opendma:ContainedInAssociations is multi-valued but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:ContainedInAssociations");
        }
        if(clazz != null && (new OdmaQName("opendma","Containable")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContainedInAssociations = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContainedInAssociations.equals(pi.getQName())) {
                        if(piDeclaredContainedInAssociations == null) {
                            piDeclaredContainedInAssociations = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:ContainedInAssociations");
                        }
                    }
                }
            }
            if(piDeclaredContainedInAssociations == null) {
                result.add("Declared properties in class have no property info object with qname opendma:ContainedInAssociations");
            }
            if(piDeclaredContainedInAssociations != null) {
                if(!"opendma".equals(piDeclaredContainedInAssociations.getNamespace())) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties qname namespace is not 'opendma'");
                }
                if(!"ContainedInAssociations".equals(piDeclaredContainedInAssociations.getName())) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties qname name is not 'ContainedInAssociations'");
                }
                if(piDeclaredContainedInAssociations.getDataType() != 10) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties data type is not '10'");
                }
                if(piDeclaredContainedInAssociations.isMultiValue() != true) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties MultiValue is not 'true'");
                }
                if(piDeclaredContainedInAssociations.isReadOnly() != true) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties ReadOnly is not 'true'");
                }
                if(!(new OdmaQName("opendma","Association")).equals(piDeclaredContainedInAssociations.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredContainedInAssociations.isHidden() != false) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContainedInAssociations.isRequired() != false) {
                result.add("Property info for opendma:ContainedInAssociations in declared properties Required is not 'false'");
                }
                if(piDeclaredContainedInAssociations.isSystem() != true) {
                    result.add("Property info for opendma:ContainedInAssociations in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContainedInAssociations = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContainedInAssociations.equals(pi.getQName())) {
                    if(piAllContainedInAssociations == null) {
                        piAllContainedInAssociations = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:ContainedInAssociations");
                    }
                }
            }
        }
        if(piAllContainedInAssociations == null) {
            result.add("All properties in class have no property info object with qname opendma:ContainedInAssociations");
        }
        if(piAllContainedInAssociations != null) {
            if(!"opendma".equals(piAllContainedInAssociations.getNamespace())) {
                result.add("Property info for opendma:ContainedInAssociations in all properties qname namespace is not 'opendma'");
            }
            if(!"ContainedInAssociations".equals(piAllContainedInAssociations.getName())) {
                result.add("Property info for opendma:ContainedInAssociations in all properties qname name is not 'ContainedInAssociations'");
            }
            if(piAllContainedInAssociations.getDataType() != 10) {
                result.add("Property info for opendma:ContainedInAssociations in all properties data type is not '10'");
            }
            if(piAllContainedInAssociations.isMultiValue() != true) {
                result.add("Property info for opendma:ContainedInAssociations in all properties MultiValue is not 'true'");
            }
            if(piAllContainedInAssociations.isReadOnly() != true) {
                result.add("Property info for opendma:ContainedInAssociations in all properties ReadOnly is not 'true'");
            }
            if(!(new OdmaQName("opendma","Association")).equals(piAllContainedInAssociations.getReferenceClass().getQName())) {
                result.add("Property info for opendma:ContainedInAssociations in all properties ReadOnly is not 'true'");
            }
            if(piAllContainedInAssociations.isHidden() != false) {
                result.add("Property info for opendma:ContainedInAssociations in all properties Hidden is not 'false'");
            }
            if(piAllContainedInAssociations.isRequired() != false) {
                result.add("Property info for opendma:ContainedInAssociations in all properties Required is not 'false'");
            }
            if(piAllContainedInAssociations.isSystem() != true) {
                result.add("Property info for opendma:ContainedInAssociations in all properties System is not 'true'");
            }
        }
        return result;
    }

    public static List<String> verifyOdmaAssociation(OdmaObject obj) {
        LinkedList<String> result = new LinkedList<>();
        if(!(obj instanceof OdmaAssociation)) {
            result.add("Does not implement OdmaAssociation interface");
        }
        result.addAll(verifyObjectBaseline(obj));
        result.addAll(verifyOdmaObject(obj));
        OdmaClass clazz = obj.getOdmaClass();
        Iterable<OdmaPropertyInfo> declaredProperties = clazz != null ? clazz.getDeclaredProperties() : null;
        Iterable<OdmaPropertyInfo> allProperties = clazz != null ? clazz.getProperties() : null;
        // opendma:Name
        OdmaQName qnameName = new OdmaQName("opendma","Name");
        try {
            OdmaProperty propName = obj.getProperty(qnameName);
            if(propName.getName() == null) {
                result.add("Property opendma:Name qname is null");
            }
            if(!"opendma".equals(propName.getName().getNamespace())) {
                result.add("Property opendma:Name qname namespace is not 'opendma', found instead'"+propName.getName().getNamespace()+"'");
            }
            if(!"Name".equals(propName.getName().getName())) {
                result.add("Property opendma:Name qname name is not 'Name', found instead'"+propName.getName().getName()+"'");
            }
            if(propName.getType() != OdmaType.STRING) {
                result.add("Property opendma:Name type is not 'STRING'");
            }
            if(propName.isMultiValue() != false) {
                result.add("Property opendma:Name MultiValue is not 'false'");
            }
            if(propName.getValue() == null) {
                result.add("Property opendma:Name is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Name");
        }
        if(clazz != null && (new OdmaQName("opendma","Association")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredName = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameName.equals(pi.getQName())) {
                        if(piDeclaredName == null) {
                            piDeclaredName = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Name");
                        }
                    }
                }
            }
            if(piDeclaredName == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Name");
            }
            if(piDeclaredName != null) {
                if(!"opendma".equals(piDeclaredName.getNamespace())) {
                    result.add("Property info for opendma:Name in declared properties qname namespace is not 'opendma'");
                }
                if(!"Name".equals(piDeclaredName.getName())) {
                    result.add("Property info for opendma:Name in declared properties qname name is not 'Name'");
                }
                if(piDeclaredName.getDataType() != 1) {
                    result.add("Property info for opendma:Name in declared properties data type is not '1'");
                }
                if(piDeclaredName.isMultiValue() != false) {
                    result.add("Property info for opendma:Name in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredName.isReadOnly() != false) {
                    result.add("Property info for opendma:Name in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredName.isHidden() != false) {
                    result.add("Property info for opendma:Name in declared properties Hidden is not 'false'");
                }
                if(piDeclaredName.isRequired() != true) {
                result.add("Property info for opendma:Name in declared properties Required is not 'true'");
                }
                if(piDeclaredName.isSystem() != true) {
                    result.add("Property info for opendma:Name in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllName = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameName.equals(pi.getQName())) {
                    if(piAllName == null) {
                        piAllName = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Name");
                    }
                }
            }
        }
        if(piAllName == null) {
            result.add("All properties in class have no property info object with qname opendma:Name");
        }
        if(piAllName != null) {
            if(!"opendma".equals(piAllName.getNamespace())) {
                result.add("Property info for opendma:Name in all properties qname namespace is not 'opendma'");
            }
            if(!"Name".equals(piAllName.getName())) {
                result.add("Property info for opendma:Name in all properties qname name is not 'Name'");
            }
            if(piAllName.getDataType() != 1) {
                result.add("Property info for opendma:Name in all properties data type is not '1'");
            }
            if(piAllName.isMultiValue() != false) {
                result.add("Property info for opendma:Name in all properties MultiValue is not 'false'");
            }
            if(piAllName.isReadOnly() != false) {
                result.add("Property info for opendma:Name in all properties ReadOnly is not 'false'");
            }
            if(piAllName.isHidden() != false) {
                result.add("Property info for opendma:Name in all properties Hidden is not 'false'");
            }
            if(piAllName.isRequired() != true) {
                result.add("Property info for opendma:Name in all properties Required is not 'true'");
            }
            if(piAllName.isSystem() != true) {
                result.add("Property info for opendma:Name in all properties System is not 'true'");
            }
        }
        // opendma:Container
        OdmaQName qnameContainer = new OdmaQName("opendma","Container");
        try {
            OdmaProperty propContainer = obj.getProperty(qnameContainer);
            if(propContainer.getName() == null) {
                result.add("Property opendma:Container qname is null");
            }
            if(!"opendma".equals(propContainer.getName().getNamespace())) {
                result.add("Property opendma:Container qname namespace is not 'opendma', found instead'"+propContainer.getName().getNamespace()+"'");
            }
            if(!"Container".equals(propContainer.getName().getName())) {
                result.add("Property opendma:Container qname name is not 'Container', found instead'"+propContainer.getName().getName()+"'");
            }
            if(propContainer.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Container type is not 'REFERENCE'");
            }
            if(propContainer.isMultiValue() != false) {
                result.add("Property opendma:Container MultiValue is not 'false'");
            }
            if(propContainer.getValue() == null) {
                result.add("Property opendma:Container is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Container");
        }
        if(clazz != null && (new OdmaQName("opendma","Association")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContainer = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContainer.equals(pi.getQName())) {
                        if(piDeclaredContainer == null) {
                            piDeclaredContainer = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Container");
                        }
                    }
                }
            }
            if(piDeclaredContainer == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Container");
            }
            if(piDeclaredContainer != null) {
                if(!"opendma".equals(piDeclaredContainer.getNamespace())) {
                    result.add("Property info for opendma:Container in declared properties qname namespace is not 'opendma'");
                }
                if(!"Container".equals(piDeclaredContainer.getName())) {
                    result.add("Property info for opendma:Container in declared properties qname name is not 'Container'");
                }
                if(piDeclaredContainer.getDataType() != 10) {
                    result.add("Property info for opendma:Container in declared properties data type is not '10'");
                }
                if(piDeclaredContainer.isMultiValue() != false) {
                    result.add("Property info for opendma:Container in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredContainer.isReadOnly() != false) {
                    result.add("Property info for opendma:Container in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","Container")).equals(piDeclaredContainer.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Container in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredContainer.isHidden() != false) {
                    result.add("Property info for opendma:Container in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContainer.isRequired() != true) {
                result.add("Property info for opendma:Container in declared properties Required is not 'true'");
                }
                if(piDeclaredContainer.isSystem() != true) {
                    result.add("Property info for opendma:Container in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContainer = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContainer.equals(pi.getQName())) {
                    if(piAllContainer == null) {
                        piAllContainer = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Container");
                    }
                }
            }
        }
        if(piAllContainer == null) {
            result.add("All properties in class have no property info object with qname opendma:Container");
        }
        if(piAllContainer != null) {
            if(!"opendma".equals(piAllContainer.getNamespace())) {
                result.add("Property info for opendma:Container in all properties qname namespace is not 'opendma'");
            }
            if(!"Container".equals(piAllContainer.getName())) {
                result.add("Property info for opendma:Container in all properties qname name is not 'Container'");
            }
            if(piAllContainer.getDataType() != 10) {
                result.add("Property info for opendma:Container in all properties data type is not '10'");
            }
            if(piAllContainer.isMultiValue() != false) {
                result.add("Property info for opendma:Container in all properties MultiValue is not 'false'");
            }
            if(piAllContainer.isReadOnly() != false) {
                result.add("Property info for opendma:Container in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","Container")).equals(piAllContainer.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Container in all properties ReadOnly is not 'false'");
            }
            if(piAllContainer.isHidden() != false) {
                result.add("Property info for opendma:Container in all properties Hidden is not 'false'");
            }
            if(piAllContainer.isRequired() != true) {
                result.add("Property info for opendma:Container in all properties Required is not 'true'");
            }
            if(piAllContainer.isSystem() != true) {
                result.add("Property info for opendma:Container in all properties System is not 'true'");
            }
        }
        // opendma:Containable
        OdmaQName qnameContainable = new OdmaQName("opendma","Containable");
        try {
            OdmaProperty propContainable = obj.getProperty(qnameContainable);
            if(propContainable.getName() == null) {
                result.add("Property opendma:Containable qname is null");
            }
            if(!"opendma".equals(propContainable.getName().getNamespace())) {
                result.add("Property opendma:Containable qname namespace is not 'opendma', found instead'"+propContainable.getName().getNamespace()+"'");
            }
            if(!"Containable".equals(propContainable.getName().getName())) {
                result.add("Property opendma:Containable qname name is not 'Containable', found instead'"+propContainable.getName().getName()+"'");
            }
            if(propContainable.getType() != OdmaType.REFERENCE) {
                result.add("Property opendma:Containable type is not 'REFERENCE'");
            }
            if(propContainable.isMultiValue() != false) {
                result.add("Property opendma:Containable MultiValue is not 'false'");
            }
            if(propContainable.getValue() == null) {
                result.add("Property opendma:Containable is required but value is null");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:Containable");
        }
        if(clazz != null && (new OdmaQName("opendma","Association")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredContainable = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameContainable.equals(pi.getQName())) {
                        if(piDeclaredContainable == null) {
                            piDeclaredContainable = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:Containable");
                        }
                    }
                }
            }
            if(piDeclaredContainable == null) {
                result.add("Declared properties in class have no property info object with qname opendma:Containable");
            }
            if(piDeclaredContainable != null) {
                if(!"opendma".equals(piDeclaredContainable.getNamespace())) {
                    result.add("Property info for opendma:Containable in declared properties qname namespace is not 'opendma'");
                }
                if(!"Containable".equals(piDeclaredContainable.getName())) {
                    result.add("Property info for opendma:Containable in declared properties qname name is not 'Containable'");
                }
                if(piDeclaredContainable.getDataType() != 10) {
                    result.add("Property info for opendma:Containable in declared properties data type is not '10'");
                }
                if(piDeclaredContainable.isMultiValue() != false) {
                    result.add("Property info for opendma:Containable in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredContainable.isReadOnly() != false) {
                    result.add("Property info for opendma:Containable in declared properties ReadOnly is not 'false'");
                }
                if(!(new OdmaQName("opendma","Containable")).equals(piDeclaredContainable.getReferenceClass().getQName())) {
                    result.add("Property info for opendma:Containable in declared properties ReadOnly is not 'false'");
                }
                if(piDeclaredContainable.isHidden() != false) {
                    result.add("Property info for opendma:Containable in declared properties Hidden is not 'false'");
                }
                if(piDeclaredContainable.isRequired() != true) {
                result.add("Property info for opendma:Containable in declared properties Required is not 'true'");
                }
                if(piDeclaredContainable.isSystem() != true) {
                    result.add("Property info for opendma:Containable in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllContainable = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameContainable.equals(pi.getQName())) {
                    if(piAllContainable == null) {
                        piAllContainable = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:Containable");
                    }
                }
            }
        }
        if(piAllContainable == null) {
            result.add("All properties in class have no property info object with qname opendma:Containable");
        }
        if(piAllContainable != null) {
            if(!"opendma".equals(piAllContainable.getNamespace())) {
                result.add("Property info for opendma:Containable in all properties qname namespace is not 'opendma'");
            }
            if(!"Containable".equals(piAllContainable.getName())) {
                result.add("Property info for opendma:Containable in all properties qname name is not 'Containable'");
            }
            if(piAllContainable.getDataType() != 10) {
                result.add("Property info for opendma:Containable in all properties data type is not '10'");
            }
            if(piAllContainable.isMultiValue() != false) {
                result.add("Property info for opendma:Containable in all properties MultiValue is not 'false'");
            }
            if(piAllContainable.isReadOnly() != false) {
                result.add("Property info for opendma:Containable in all properties ReadOnly is not 'false'");
            }
            if(!(new OdmaQName("opendma","Containable")).equals(piAllContainable.getReferenceClass().getQName())) {
                result.add("Property info for opendma:Containable in all properties ReadOnly is not 'false'");
            }
            if(piAllContainable.isHidden() != false) {
                result.add("Property info for opendma:Containable in all properties Hidden is not 'false'");
            }
            if(piAllContainable.isRequired() != true) {
                result.add("Property info for opendma:Containable in all properties Required is not 'true'");
            }
            if(piAllContainable.isSystem() != true) {
                result.add("Property info for opendma:Containable in all properties System is not 'true'");
            }
        }
        // opendma:CreatedAt
        OdmaQName qnameCreatedAt = new OdmaQName("opendma","CreatedAt");
        try {
            OdmaProperty propCreatedAt = obj.getProperty(qnameCreatedAt);
            if(propCreatedAt.getName() == null) {
                result.add("Property opendma:CreatedAt qname is null");
            }
            if(!"opendma".equals(propCreatedAt.getName().getNamespace())) {
                result.add("Property opendma:CreatedAt qname namespace is not 'opendma', found instead'"+propCreatedAt.getName().getNamespace()+"'");
            }
            if(!"CreatedAt".equals(propCreatedAt.getName().getName())) {
                result.add("Property opendma:CreatedAt qname name is not 'CreatedAt', found instead'"+propCreatedAt.getName().getName()+"'");
            }
            if(propCreatedAt.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:CreatedAt type is not 'DATETIME'");
            }
            if(propCreatedAt.isMultiValue() != false) {
                result.add("Property opendma:CreatedAt MultiValue is not 'false'");
            }
            if(!propCreatedAt.isReadOnly()) {
                result.add("Property opendma:CreatedAt ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CreatedAt");
        }
        if(clazz != null && (new OdmaQName("opendma","Association")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCreatedAt = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCreatedAt.equals(pi.getQName())) {
                        if(piDeclaredCreatedAt == null) {
                            piDeclaredCreatedAt = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CreatedAt");
                        }
                    }
                }
            }
            if(piDeclaredCreatedAt == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CreatedAt");
            }
            if(piDeclaredCreatedAt != null) {
                if(!"opendma".equals(piDeclaredCreatedAt.getNamespace())) {
                    result.add("Property info for opendma:CreatedAt in declared properties qname namespace is not 'opendma'");
                }
                if(!"CreatedAt".equals(piDeclaredCreatedAt.getName())) {
                    result.add("Property info for opendma:CreatedAt in declared properties qname name is not 'CreatedAt'");
                }
                if(piDeclaredCreatedAt.getDataType() != 8) {
                    result.add("Property info for opendma:CreatedAt in declared properties data type is not '8'");
                }
                if(piDeclaredCreatedAt.isMultiValue() != false) {
                    result.add("Property info for opendma:CreatedAt in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCreatedAt.isReadOnly() != true) {
                    result.add("Property info for opendma:CreatedAt in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCreatedAt.isHidden() != false) {
                    result.add("Property info for opendma:CreatedAt in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCreatedAt.isRequired() != false) {
                result.add("Property info for opendma:CreatedAt in declared properties Required is not 'false'");
                }
                if(piDeclaredCreatedAt.isSystem() != true) {
                    result.add("Property info for opendma:CreatedAt in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCreatedAt = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCreatedAt.equals(pi.getQName())) {
                    if(piAllCreatedAt == null) {
                        piAllCreatedAt = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CreatedAt");
                    }
                }
            }
        }
        if(piAllCreatedAt == null) {
            result.add("All properties in class have no property info object with qname opendma:CreatedAt");
        }
        if(piAllCreatedAt != null) {
            if(!"opendma".equals(piAllCreatedAt.getNamespace())) {
                result.add("Property info for opendma:CreatedAt in all properties qname namespace is not 'opendma'");
            }
            if(!"CreatedAt".equals(piAllCreatedAt.getName())) {
                result.add("Property info for opendma:CreatedAt in all properties qname name is not 'CreatedAt'");
            }
            if(piAllCreatedAt.getDataType() != 8) {
                result.add("Property info for opendma:CreatedAt in all properties data type is not '8'");
            }
            if(piAllCreatedAt.isMultiValue() != false) {
                result.add("Property info for opendma:CreatedAt in all properties MultiValue is not 'false'");
            }
            if(piAllCreatedAt.isReadOnly() != true) {
                result.add("Property info for opendma:CreatedAt in all properties ReadOnly is not 'true'");
            }
            if(piAllCreatedAt.isHidden() != false) {
                result.add("Property info for opendma:CreatedAt in all properties Hidden is not 'false'");
            }
            if(piAllCreatedAt.isRequired() != false) {
                result.add("Property info for opendma:CreatedAt in all properties Required is not 'false'");
            }
            if(piAllCreatedAt.isSystem() != true) {
                result.add("Property info for opendma:CreatedAt in all properties System is not 'true'");
            }
        }
        // opendma:CreatedBy
        OdmaQName qnameCreatedBy = new OdmaQName("opendma","CreatedBy");
        try {
            OdmaProperty propCreatedBy = obj.getProperty(qnameCreatedBy);
            if(propCreatedBy.getName() == null) {
                result.add("Property opendma:CreatedBy qname is null");
            }
            if(!"opendma".equals(propCreatedBy.getName().getNamespace())) {
                result.add("Property opendma:CreatedBy qname namespace is not 'opendma', found instead'"+propCreatedBy.getName().getNamespace()+"'");
            }
            if(!"CreatedBy".equals(propCreatedBy.getName().getName())) {
                result.add("Property opendma:CreatedBy qname name is not 'CreatedBy', found instead'"+propCreatedBy.getName().getName()+"'");
            }
            if(propCreatedBy.getType() != OdmaType.STRING) {
                result.add("Property opendma:CreatedBy type is not 'STRING'");
            }
            if(propCreatedBy.isMultiValue() != false) {
                result.add("Property opendma:CreatedBy MultiValue is not 'false'");
            }
            if(!propCreatedBy.isReadOnly()) {
                result.add("Property opendma:CreatedBy ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:CreatedBy");
        }
        if(clazz != null && (new OdmaQName("opendma","Association")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredCreatedBy = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameCreatedBy.equals(pi.getQName())) {
                        if(piDeclaredCreatedBy == null) {
                            piDeclaredCreatedBy = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:CreatedBy");
                        }
                    }
                }
            }
            if(piDeclaredCreatedBy == null) {
                result.add("Declared properties in class have no property info object with qname opendma:CreatedBy");
            }
            if(piDeclaredCreatedBy != null) {
                if(!"opendma".equals(piDeclaredCreatedBy.getNamespace())) {
                    result.add("Property info for opendma:CreatedBy in declared properties qname namespace is not 'opendma'");
                }
                if(!"CreatedBy".equals(piDeclaredCreatedBy.getName())) {
                    result.add("Property info for opendma:CreatedBy in declared properties qname name is not 'CreatedBy'");
                }
                if(piDeclaredCreatedBy.getDataType() != 1) {
                    result.add("Property info for opendma:CreatedBy in declared properties data type is not '1'");
                }
                if(piDeclaredCreatedBy.isMultiValue() != false) {
                    result.add("Property info for opendma:CreatedBy in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredCreatedBy.isReadOnly() != true) {
                    result.add("Property info for opendma:CreatedBy in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredCreatedBy.isHidden() != false) {
                    result.add("Property info for opendma:CreatedBy in declared properties Hidden is not 'false'");
                }
                if(piDeclaredCreatedBy.isRequired() != false) {
                result.add("Property info for opendma:CreatedBy in declared properties Required is not 'false'");
                }
                if(piDeclaredCreatedBy.isSystem() != true) {
                    result.add("Property info for opendma:CreatedBy in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllCreatedBy = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameCreatedBy.equals(pi.getQName())) {
                    if(piAllCreatedBy == null) {
                        piAllCreatedBy = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:CreatedBy");
                    }
                }
            }
        }
        if(piAllCreatedBy == null) {
            result.add("All properties in class have no property info object with qname opendma:CreatedBy");
        }
        if(piAllCreatedBy != null) {
            if(!"opendma".equals(piAllCreatedBy.getNamespace())) {
                result.add("Property info for opendma:CreatedBy in all properties qname namespace is not 'opendma'");
            }
            if(!"CreatedBy".equals(piAllCreatedBy.getName())) {
                result.add("Property info for opendma:CreatedBy in all properties qname name is not 'CreatedBy'");
            }
            if(piAllCreatedBy.getDataType() != 1) {
                result.add("Property info for opendma:CreatedBy in all properties data type is not '1'");
            }
            if(piAllCreatedBy.isMultiValue() != false) {
                result.add("Property info for opendma:CreatedBy in all properties MultiValue is not 'false'");
            }
            if(piAllCreatedBy.isReadOnly() != true) {
                result.add("Property info for opendma:CreatedBy in all properties ReadOnly is not 'true'");
            }
            if(piAllCreatedBy.isHidden() != false) {
                result.add("Property info for opendma:CreatedBy in all properties Hidden is not 'false'");
            }
            if(piAllCreatedBy.isRequired() != false) {
                result.add("Property info for opendma:CreatedBy in all properties Required is not 'false'");
            }
            if(piAllCreatedBy.isSystem() != true) {
                result.add("Property info for opendma:CreatedBy in all properties System is not 'true'");
            }
        }
        // opendma:LastModifiedAt
        OdmaQName qnameLastModifiedAt = new OdmaQName("opendma","LastModifiedAt");
        try {
            OdmaProperty propLastModifiedAt = obj.getProperty(qnameLastModifiedAt);
            if(propLastModifiedAt.getName() == null) {
                result.add("Property opendma:LastModifiedAt qname is null");
            }
            if(!"opendma".equals(propLastModifiedAt.getName().getNamespace())) {
                result.add("Property opendma:LastModifiedAt qname namespace is not 'opendma', found instead'"+propLastModifiedAt.getName().getNamespace()+"'");
            }
            if(!"LastModifiedAt".equals(propLastModifiedAt.getName().getName())) {
                result.add("Property opendma:LastModifiedAt qname name is not 'LastModifiedAt', found instead'"+propLastModifiedAt.getName().getName()+"'");
            }
            if(propLastModifiedAt.getType() != OdmaType.DATETIME) {
                result.add("Property opendma:LastModifiedAt type is not 'DATETIME'");
            }
            if(propLastModifiedAt.isMultiValue() != false) {
                result.add("Property opendma:LastModifiedAt MultiValue is not 'false'");
            }
            if(!propLastModifiedAt.isReadOnly()) {
                result.add("Property opendma:LastModifiedAt ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:LastModifiedAt");
        }
        if(clazz != null && (new OdmaQName("opendma","Association")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLastModifiedAt = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLastModifiedAt.equals(pi.getQName())) {
                        if(piDeclaredLastModifiedAt == null) {
                            piDeclaredLastModifiedAt = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:LastModifiedAt");
                        }
                    }
                }
            }
            if(piDeclaredLastModifiedAt == null) {
                result.add("Declared properties in class have no property info object with qname opendma:LastModifiedAt");
            }
            if(piDeclaredLastModifiedAt != null) {
                if(!"opendma".equals(piDeclaredLastModifiedAt.getNamespace())) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties qname namespace is not 'opendma'");
                }
                if(!"LastModifiedAt".equals(piDeclaredLastModifiedAt.getName())) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties qname name is not 'LastModifiedAt'");
                }
                if(piDeclaredLastModifiedAt.getDataType() != 8) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties data type is not '8'");
                }
                if(piDeclaredLastModifiedAt.isMultiValue() != false) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isReadOnly() != true) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredLastModifiedAt.isHidden() != false) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedAt in declared properties Required is not 'false'");
                }
                if(piDeclaredLastModifiedAt.isSystem() != true) {
                    result.add("Property info for opendma:LastModifiedAt in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLastModifiedAt = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLastModifiedAt.equals(pi.getQName())) {
                    if(piAllLastModifiedAt == null) {
                        piAllLastModifiedAt = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:LastModifiedAt");
                    }
                }
            }
        }
        if(piAllLastModifiedAt == null) {
            result.add("All properties in class have no property info object with qname opendma:LastModifiedAt");
        }
        if(piAllLastModifiedAt != null) {
            if(!"opendma".equals(piAllLastModifiedAt.getNamespace())) {
                result.add("Property info for opendma:LastModifiedAt in all properties qname namespace is not 'opendma'");
            }
            if(!"LastModifiedAt".equals(piAllLastModifiedAt.getName())) {
                result.add("Property info for opendma:LastModifiedAt in all properties qname name is not 'LastModifiedAt'");
            }
            if(piAllLastModifiedAt.getDataType() != 8) {
                result.add("Property info for opendma:LastModifiedAt in all properties data type is not '8'");
            }
            if(piAllLastModifiedAt.isMultiValue() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties MultiValue is not 'false'");
            }
            if(piAllLastModifiedAt.isReadOnly() != true) {
                result.add("Property info for opendma:LastModifiedAt in all properties ReadOnly is not 'true'");
            }
            if(piAllLastModifiedAt.isHidden() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties Hidden is not 'false'");
            }
            if(piAllLastModifiedAt.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedAt in all properties Required is not 'false'");
            }
            if(piAllLastModifiedAt.isSystem() != true) {
                result.add("Property info for opendma:LastModifiedAt in all properties System is not 'true'");
            }
        }
        // opendma:LastModifiedBy
        OdmaQName qnameLastModifiedBy = new OdmaQName("opendma","LastModifiedBy");
        try {
            OdmaProperty propLastModifiedBy = obj.getProperty(qnameLastModifiedBy);
            if(propLastModifiedBy.getName() == null) {
                result.add("Property opendma:LastModifiedBy qname is null");
            }
            if(!"opendma".equals(propLastModifiedBy.getName().getNamespace())) {
                result.add("Property opendma:LastModifiedBy qname namespace is not 'opendma', found instead'"+propLastModifiedBy.getName().getNamespace()+"'");
            }
            if(!"LastModifiedBy".equals(propLastModifiedBy.getName().getName())) {
                result.add("Property opendma:LastModifiedBy qname name is not 'LastModifiedBy', found instead'"+propLastModifiedBy.getName().getName()+"'");
            }
            if(propLastModifiedBy.getType() != OdmaType.STRING) {
                result.add("Property opendma:LastModifiedBy type is not 'STRING'");
            }
            if(propLastModifiedBy.isMultiValue() != false) {
                result.add("Property opendma:LastModifiedBy MultiValue is not 'false'");
            }
            if(!propLastModifiedBy.isReadOnly()) {
                result.add("Property opendma:LastModifiedBy ReadOnly must be 'true'");
            }
        } catch(OdmaPropertyNotFoundException pnfe) {
            result.add("Missing property opendma:LastModifiedBy");
        }
        if(clazz != null && (new OdmaQName("opendma","Association")).equals(clazz.getQName())) {
            OdmaPropertyInfo piDeclaredLastModifiedBy = null;
            if(declaredProperties != null) {
                for(OdmaPropertyInfo pi : declaredProperties) {
                    if(qnameLastModifiedBy.equals(pi.getQName())) {
                        if(piDeclaredLastModifiedBy == null) {
                            piDeclaredLastModifiedBy = pi;
                        } else {
                            result.add("Declared properties in class have multiple property info objects with qname opendma:LastModifiedBy");
                        }
                    }
                }
            }
            if(piDeclaredLastModifiedBy == null) {
                result.add("Declared properties in class have no property info object with qname opendma:LastModifiedBy");
            }
            if(piDeclaredLastModifiedBy != null) {
                if(!"opendma".equals(piDeclaredLastModifiedBy.getNamespace())) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties qname namespace is not 'opendma'");
                }
                if(!"LastModifiedBy".equals(piDeclaredLastModifiedBy.getName())) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties qname name is not 'LastModifiedBy'");
                }
                if(piDeclaredLastModifiedBy.getDataType() != 1) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties data type is not '1'");
                }
                if(piDeclaredLastModifiedBy.isMultiValue() != false) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties MultiValue is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isReadOnly() != true) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties ReadOnly is not 'true'");
                }
                if(piDeclaredLastModifiedBy.isHidden() != false) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties Hidden is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedBy in declared properties Required is not 'false'");
                }
                if(piDeclaredLastModifiedBy.isSystem() != true) {
                    result.add("Property info for opendma:LastModifiedBy in declared properties System is not 'true'");
                }
            }
        }
        OdmaPropertyInfo piAllLastModifiedBy = null;
        if(allProperties != null) {
            for(OdmaPropertyInfo pi : allProperties) {
                if(qnameLastModifiedBy.equals(pi.getQName())) {
                    if(piAllLastModifiedBy == null) {
                        piAllLastModifiedBy = pi;
                    } else {
                        result.add("All properties in class have multiple property info objects with qname opendma:LastModifiedBy");
                    }
                }
            }
        }
        if(piAllLastModifiedBy == null) {
            result.add("All properties in class have no property info object with qname opendma:LastModifiedBy");
        }
        if(piAllLastModifiedBy != null) {
            if(!"opendma".equals(piAllLastModifiedBy.getNamespace())) {
                result.add("Property info for opendma:LastModifiedBy in all properties qname namespace is not 'opendma'");
            }
            if(!"LastModifiedBy".equals(piAllLastModifiedBy.getName())) {
                result.add("Property info for opendma:LastModifiedBy in all properties qname name is not 'LastModifiedBy'");
            }
            if(piAllLastModifiedBy.getDataType() != 1) {
                result.add("Property info for opendma:LastModifiedBy in all properties data type is not '1'");
            }
            if(piAllLastModifiedBy.isMultiValue() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties MultiValue is not 'false'");
            }
            if(piAllLastModifiedBy.isReadOnly() != true) {
                result.add("Property info for opendma:LastModifiedBy in all properties ReadOnly is not 'true'");
            }
            if(piAllLastModifiedBy.isHidden() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties Hidden is not 'false'");
            }
            if(piAllLastModifiedBy.isRequired() != false) {
                result.add("Property info for opendma:LastModifiedBy in all properties Required is not 'false'");
            }
            if(piAllLastModifiedBy.isSystem() != true) {
                result.add("Property info for opendma:LastModifiedBy in all properties System is not 'true'");
            }
        }
        return result;
    }

}
