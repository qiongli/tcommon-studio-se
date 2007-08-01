// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.core.model.metadata.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.talend.core.model.metadata.MetadataTalendType;

/**
 * qzhang class global comment. Detailled comment <br/>
 * 
 */
public class ContextParameterJavaTypeManager {

    public static final JavaType[] JAVA_TYPES = new JavaType[] { JavaTypesManager.BOOLEAN, JavaTypesManager.CHARACTER,
            JavaTypesManager.DATE, JavaTypesManager.DOUBLE, JavaTypesManager.FLOAT, JavaTypesManager.INTEGER,
            JavaTypesManager.LONG, JavaTypesManager.SHORT, JavaTypesManager.STRING, JavaTypesManager.FILE,
            JavaTypesManager.DIRECTORY };

    public static final String PERL_FILE = "File";

    public static final String PERL_DIRECTORY = "Directory";

    public static final String PERL_DAY = "Day";

    private static Map<String, JavaType> shortNameToJavaType;

    private static Map<String, JavaType> canonicalClassNameToJavaType;

    private static Map<String, JavaType> labelToJavaType;

    private static Map<String, JavaType> idToJavaType;

    private static List<JavaType> javaTypes;

    private static List<String> perlTypes;

    private static String[] javaTypesLabelsArray = new String[0];

    private static final List<String> JAVA_PRIMITIVE_TYPES = new ArrayList<String>();

    private static final Set<String> PRIMITIVE_TYPES_SET = new HashSet<String>(JAVA_PRIMITIVE_TYPES);

    static {
        init();
    }

    /**
     * DOC amaumont Comment method "init".
     */
    private static void init() {

        shortNameToJavaType = new HashMap<String, JavaType>();
        labelToJavaType = new HashMap<String, JavaType>();
        idToJavaType = new HashMap<String, JavaType>();
        canonicalClassNameToJavaType = new HashMap<String, JavaType>();
        javaTypes = new ArrayList<JavaType>();

        perlTypes = new ArrayList<String>();
        for (int i = 0; i < JAVA_TYPES.length; i++) {
            JavaType javaType = JAVA_TYPES[i];
            addJavaType(javaType);
        }
        final String[] loadTalendTypes = MetadataTalendType.getTalendTypesLabels(); //$NON-NLS-1$
        perlTypes.addAll(Arrays.asList(loadTalendTypes));
        perlTypes.add(PERL_FILE);
        perlTypes.add(PERL_DIRECTORY);
    }

    /**
     * Add a java type to the common list.
     * 
     * @param javaType
     */
    public static void addJavaType(JavaType javaType) {
        String primitiveName = null;
        Class primitiveClass = javaType.getPrimitiveClass();
        if (primitiveClass != null) {
            primitiveName = primitiveClass.getSimpleName();
            shortNameToJavaType.put(primitiveName, javaType);
            canonicalClassNameToJavaType.put(primitiveClass.getCanonicalName(), javaType);
            JAVA_PRIMITIVE_TYPES.add(primitiveClass.getSimpleName());
        }
        String nullableName = javaType.getNullableClass().getSimpleName();
        shortNameToJavaType.put(nullableName, javaType);
        canonicalClassNameToJavaType.put(javaType.getNullableClass().getCanonicalName(), javaType);
        labelToJavaType.put(javaType.getLabel(), javaType);
        idToJavaType.put(javaType.getId(), javaType);
        javaTypes.add(javaType);
    }

    public static JavaType getJavaTypeFromName(String typeName) {
        return shortNameToJavaType.get(typeName);
    }

    public static String getShortNameFromJavaType(JavaType javaType) {
        Class primitiveClass = javaType.getPrimitiveClass();
        if (primitiveClass != null) {
            return primitiveClass.getSimpleName();
        }
        return javaType.getNullableClass().getSimpleName();
    }

    /**
     * 
     * Search JavaType from label (ex: "double / Double" or "String").
     * 
     * @param typeName
     * @return JavaType if found, else null
     */
    public static JavaType getJavaTypeFromLabel(String label) {
        return labelToJavaType.get(label);
    }

    /**
     * 
     * Search JavaType from label (ex: "double / Double" or "String").
     * 
     * @param typeName
     * @return JavaType if found, else null
     */
    public static JavaType getJavaTypeFromId(String id) {
        JavaType javaTypeFromId = idToJavaType.get(id);
        if (javaTypeFromId == null) {
            throw new IllegalArgumentException("Unknown java id type : '" + id + "'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return javaTypeFromId;
    }

    public static String[] getJavaTypesLabels() {
        int lstSize = javaTypes.size();
        if (javaTypesLabelsArray.length != lstSize) {
            javaTypesLabelsArray = new String[lstSize];
            for (int i = 0; i < lstSize; i++) {
                javaTypesLabelsArray[i] = javaTypes.get(i).getLabel();
            }
        }
        return (String[]) ArrayUtils.clone(javaTypesLabelsArray);
    }

    public static JavaType[] getJavaTypes() {
        return javaTypes.toArray(new JavaType[0]);
    }

    /**
     * 
     * Search JavaType from short name (ex: "Double" or "double").
     * 
     * @param typeName
     * @return JavaType if found, else null
     */
    public static JavaType getJavaTypeFromCanonicalName(String canonicalName) {
        return canonicalClassNameToJavaType.get(canonicalName);
    }

    /**
     * 
     * Get primitive or object type according to id and nullable parameters.
     * 
     * @param idType
     * @param nullable
     * @return canonical name of class (java.lang.String or int)
     */
    public static String getTypeToGenerate(String idType, boolean nullable) {
        JavaType javaTypeFromId = getJavaTypeFromId(idType);
        return getTypeToGenerate(javaTypeFromId, nullable);
    }

    /**
     * DOC amaumont Comment method "getFinalType".
     * 
     * @param javaType
     * @param nullable
     * @return
     */
    private static String getTypeToGenerate(JavaType javaType, boolean nullable) {
        if (javaType == null) {
            return null;
        }
        Class primitiveClass = javaType.getPrimitiveClass();
        Class nullableClass = javaType.getNullableClass();
        if (nullable) {
            if (javaType.isGenerateWithCanonicalName()) {
                return nullableClass.getCanonicalName();
            } else {
                return nullableClass.getSimpleName();
            }
        } else {
            if (primitiveClass != null) {
                return javaType.getPrimitiveClass().getSimpleName();
            } else {
                if (javaType.isGenerateWithCanonicalName()) {
                    return nullableClass.getCanonicalName();
                } else {
                    return nullableClass.getSimpleName();
                }
            }
        }
    }

    /**
     * 
     * Return true if given type represents a primitive java type.
     * 
     * @param type
     * @return true if given type represents a primitive java type
     */
    public static boolean isJavaPrimitiveType(String type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }
        return PRIMITIVE_TYPES_SET.contains(type);
    }

    /**
     * 
     * Return true if given type represents a primitive java type.
     * 
     * @param type
     * @return true if given type represents a primitive java type
     */
    public static boolean isJavaPrimitiveType(String idType, boolean nullable) {
        String typeToGenerate = getTypeToGenerate(idType, nullable);
        return isJavaPrimitiveType(typeToGenerate);
    }

    /**
     * 
     * Return true if given type represents a primitive java type.
     * 
     * @param type
     * @return true if given type represents a primitive java type
     */
    public static boolean isJavaPrimitiveType(JavaType javaType, boolean nullable) {
        String typeToGenerate = getTypeToGenerate(javaType, nullable);
        return isJavaPrimitiveType(typeToGenerate);
    }

    /**
     * 
     * Return the default value for a given type.
     * 
     * @param type
     * @return
     */
    public static String getDefaultValueFromJavaType(String type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }
        if (isJavaPrimitiveType(type)) {
            if (type.equals(JavaTypesManager.JAVA_PRIMITIVE_CHAR)) {
                return JavaTypesManager.DEFAULT_CHAR;
            } else if (type.equals(JavaTypesManager.JAVA_PRIMITIVE_BOOLEAN)) {
                return JavaTypesManager.DEFAULT_BOOLEAN;
            } else {
                return JavaTypesManager.DEFAULT_NUMBER;
            }
        } else {
            return JavaTypesManager.NULL;
        }
    }

    /**
     * 
     * Return the default value for a given type, if the given parameter "defaultValue" is set, this value is returned.
     * 
     * @param type
     * @return string value if the case is valid or the default value is valid. If the type is primitive and the
     * <code>defaultValue</code> is not set, java null is returned.
     */
    public static String getDefaultValueFromJavaType(String type, String defaultValue) {
        if (defaultValue != null && defaultValue.length() > 0) {
            return defaultValue;
        } else if (isJavaPrimitiveType(type)) {
            return null;
        } else {
            return getDefaultValueFromJavaType(type);
        }

    }

    /**
     * 
     * Return the default value for a given type.
     * 
     * @param type
     * @return
     */
    public static String getDefaultValueFromJavaIdType(String idType, boolean nullable) {
        String typeToGenerate = getTypeToGenerate(idType, nullable);
        return getDefaultValueFromJavaType(typeToGenerate);
    }

    /**
     * 
     * Return the default value for a given type.
     * 
     * @param type
     * @return
     */
    public static String getDefaultValueFromJavaIdType(String idType, boolean nullable, String defaultValue) {
        String typeToGenerate = getTypeToGenerate(idType, nullable);
        return getDefaultValueFromJavaType(typeToGenerate, defaultValue);
    }

    public static JavaType getDefaultJavaType() {
        return JavaTypesManager.STRING;
    }

    public static List<String> getPerlTypes() {
        return perlTypes;
    }

    /**
     * qzhang Comment method "getPerlTypesLabels".
     * 
     * @return
     */
    public static String[] getPerlTypesLabels() {
        return (String[]) ArrayUtils.clone(perlTypes.toArray(new String[0]));
    }
}
