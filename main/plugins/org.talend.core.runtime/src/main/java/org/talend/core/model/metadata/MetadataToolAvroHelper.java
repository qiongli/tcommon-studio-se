// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.SchemaBuilder.BaseFieldTypeBuilder;
import org.apache.avro.SchemaBuilder.FieldAssembler;
import org.apache.avro.SchemaBuilder.FieldBuilder;
import org.apache.avro.SchemaBuilder.PropBuilder;
import org.apache.avro.SchemaBuilder.RecordBuilder;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.daikon.avro.util.AvroTypes;
import org.talend.daikon.avro.util.AvroUtils;
import org.talend.daikon.talend6.Talend6SchemaConstants;
import orgomg.cwm.objectmodel.core.Expression;
import orgomg.cwm.objectmodel.core.TaggedValue;

/**
 * Utility classes for conversion between a {@link MetadataTable} and an Avro {@link Schema}.
 */
public final class MetadataToolAvroHelper {

    /**
     * @return An Avro schema with enriched properties from the incoming metadata table.
     */
    public static org.apache.avro.Schema convertToAvro(MetadataTable in) {
        RecordBuilder<Schema> builder = SchemaBuilder.builder().record(in.getLabel());
        copyTableProperties(builder, in);

        FieldAssembler<Schema> fa = builder.fields();
        int dynamicPosition = -1;
        org.talend.core.model.metadata.builder.connection.MetadataColumn dynColumn = null;
        int i = 0;
        for (org.talend.core.model.metadata.builder.connection.MetadataColumn column : in.getColumns()) {
            if ("id_Dynamic".equals(column.getTalendType())) { //$NON-NLS-1$
                dynamicPosition = i;
                dynColumn = column;
            } else {
                fa = convertToAvro(fa, column);
            }
            i++;
        }

        Schema schema = fa.endRecord();

        if (dynColumn != null) {
            // store all the dynamic column's properties
            schema = copyDynamicColumnProperties(schema, dynColumn);
            // store dynamic position
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_POSITION,
                    String.valueOf(dynamicPosition));
            // tag avro schema with include-all-columns
            schema = AvroUtils.setIncludeAllFields(schema, true);
        }
        return schema;
    }

    /**
     * Copy all of the information from the MetadataTable in the form of key/value properties into an Avro object.
     * 
     * @param builder Any Avro builder capable of taking key/value in the form of strings.
     * @param in The element to copy information from.
     * @return the instance of the builder passed in.
     */
    private static <T extends PropBuilder<T>> PropBuilder<T> copyTableProperties(PropBuilder<T> builder, MetadataTable in) {

        // Properties common to tables and columns.
        if (in.getId() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_ID, in.getId());
        }
        if (in.getComment() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COMMENT, in.getComment());
        }
        if (in.getLabel() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_LABEL, in.getLabel());
        }
        if (in.isReadOnly()) {
            builder.prop(Talend6SchemaConstants.TALEND6_IS_READ_ONLY, "true"); //$NON-NLS-1$
        }
        for (TaggedValue tv : in.getTaggedValue()) {
            String additionalTag = tv.getTag();
            if (tv.getValue() != null) {
                builder.prop(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES + additionalTag, tv.getValue());
            }
        }

        // Table-specific properties.
        if (in.getName() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_TABLE_NAME, in.getName());
        }
        if (in.getTableType() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_TABLE_TYPE, in.getTableType());
        }

        return builder;
    }

    /**
     * Build a field into a schema using enriched properties from the incoming column.
     */
    private static FieldAssembler<Schema> convertToAvro(FieldAssembler<Schema> fa,
            org.talend.core.model.metadata.builder.connection.MetadataColumn in) {
        FieldBuilder<Schema> fb = fa.name(in.getLabel());
        copyColumnProperties(fb, in);
        BaseFieldTypeBuilder<Schema> ftb = in.isNullable() ? fb.type().nullable() : fb.type();

        Object defaultValue = null;
        Expression initialValue = in.getInitialValue();
        if (initialValue != null) {
            defaultValue = initialValue.getBody();
        }

        String tt = in.getTalendType();

        Schema type = null;
        // Numeric types.
        if (JavaTypesManager.LONG.getId().equals(tt)) {
            type = AvroTypes._long();
            defaultValue = defaultValue == null ? null : Long.parseLong(defaultValue.toString());
        } else if (JavaTypesManager.INTEGER.getId().equals(tt)) {
            type = AvroTypes._int();
            defaultValue = defaultValue == null ? null : Integer.parseInt(defaultValue.toString());
        } else if (JavaTypesManager.SHORT.getId().equals(tt)) {
            type = AvroTypes._short();
            defaultValue = defaultValue == null ? null : Integer.parseInt(defaultValue.toString());
        } else if (JavaTypesManager.BYTE.getId().equals(tt)) {
            type = AvroTypes._byte();
            defaultValue = defaultValue == null ? null : Integer.parseInt(defaultValue.toString());
        } else if (JavaTypesManager.DOUBLE.getId().equals(tt)) {
            type = AvroTypes._double();
            defaultValue = defaultValue == null ? null : Double.parseDouble(defaultValue.toString());
        } else if (JavaTypesManager.FLOAT.getId().equals(tt)) {
            type = AvroTypes._float();
            defaultValue = defaultValue == null ? null : Float.parseFloat(defaultValue.toString());
        } else if (JavaTypesManager.BIGDECIMAL.getId().equals(tt)) {
            // decimal(precision, scale) == column length and precision?
            type = AvroTypes._decimal();
        }

        // Other primitive types that map directly to Avro.
        else if (JavaTypesManager.BOOLEAN.getId().equals(tt)) {
            type = AvroTypes._boolean();
            defaultValue = defaultValue == null ? null : Boolean.parseBoolean(defaultValue.toString());
        } else if (JavaTypesManager.BYTE_ARRAY.getId().equals(tt)) {
            type = AvroTypes._bytes();
        } else if (JavaTypesManager.DATE.getId().equals(tt)) {
            type = AvroTypes._date();
        }
        // String-ish types.
        else if (JavaTypesManager.STRING.getId().equals(tt) || JavaTypesManager.FILE.getId().equals(tt)
                || JavaTypesManager.DIRECTORY.getId().equals(tt) || JavaTypesManager.VALUE_LIST.getId().equals(tt)
                || JavaTypesManager.CHARACTER.getId().equals(tt) || JavaTypesManager.PASSWORD.getId().equals(tt)) {
            type = AvroTypes._string();
        }

        // Types with unknown elements, store as binary
        if (JavaTypesManager.OBJECT.getId().equals(tt)) {
            // FIXME it's not right, as it don't store all the information about the object
        }

        if (JavaTypesManager.LIST.getId().equals(tt)) {
            // FIXME it's not right, as it don't store all the information about the object
        }
        // Can this occur?
        if (type == null) {
            throw new UnsupportedOperationException("Unrecognized type " + tt); //$NON-NLS-1$
        }

        type = in.isNullable() ? AvroUtils.wrapAsNullable(type) : type;
        return defaultValue == null ? fb.type(type).noDefault() : fb.type(type).withDefault(defaultValue);
    }

    private static Schema copyDynamicColumnProperties(Schema schema,
            org.talend.core.model.metadata.builder.connection.MetadataColumn in) {
        Map<String, String> props = new HashMap<String, String>();
        if (in.getId() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_ID, in.getId());
        }
        if (in.getComment() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_COMMENT, in.getComment());
        }
        if (in.getLabel() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_NAME, in.getLabel());
        }
        if (in.isReadOnly()) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_DYNAMIC_IS_READ_ONLY, "true"); //$NON-NLS-1$
        }
        for (TaggedValue tv : in.getTaggedValue()) {
            String additionalTag = tv.getTag();
            if (tv.getValue() != null) {
                schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_DYNAMIC_ADDITIONAL_PROPERTIES
                        + additionalTag, tv.getValue());
            }
        }

        // Column-specific properties.
        if (in.isKey()) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_IS_KEY, "true"); //$NON-NLS-1$
        }
        if (in.getType() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_SOURCE_TYPE, in.getSourceType());
        }
        if (in.getTalendType() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_TALEND_TYPE, in.getTalendType());
        }
        if (in.getPattern() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_PATTERN,
                    TalendQuoteUtils.removeQuotesIfExist(in.getPattern()));
        }
        if (in.getLength() >= 0) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_LENGTH,
                    String.valueOf((int) in.getLength()));
        }
        if (in.getOriginalLength() >= 0) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_LENGTH,
                    String.valueOf(in.getOriginalLength()));
        }
        if (in.isNullable()) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_IS_NULLABLE, "true"); //$NON-NLS-1$
        }
        if (in.getPrecision() >= 0) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_PRECISION,
                    String.valueOf(in.getPrecision()));
        }
        if (in.getInitialValue() != null && in.getInitialValue().getBody() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_DEFAULT, in.getInitialValue().getBody());
        }
        if (in.getName() != null) {
            // keyword fixes?
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_DB_COLUMN_NAME, in.getName());
        }
        if (in.getRelatedEntity() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_RELATED_ENTITY, in.getRelatedEntity());
        }
        if (in.getRelationshipType() != null) {
            schema = AvroUtils.setProperty(schema, Talend6SchemaConstants.TALEND6_COLUMN_RELATIONSHIP_TYPE,
                    in.getRelationshipType());
        }
        return schema;
    }

    /**
     * Copy all of the information from the IMetadataColumn in the form of key/value properties into an Avro object.
     * 
     * @param builder Any Avro builder capable of taking key/value in the form of strings.
     * @param in The element to copy information from.
     * @return the instance of the builder passed in.
     */
    private static <T extends PropBuilder<T>> PropBuilder<T> copyColumnProperties(PropBuilder<T> builder,
            org.talend.core.model.metadata.builder.connection.MetadataColumn in) {
        // Properties common to tables and columns.
        if (in.getId() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_ID, in.getId());
        }
        if (in.getComment() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COMMENT, in.getComment());
        }
        if (in.getLabel() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_LABEL, in.getLabel());
        }
        if (in.isReadOnly()) {
            builder.prop(Talend6SchemaConstants.TALEND6_IS_READ_ONLY, "true"); //$NON-NLS-1$
        }
        for (TaggedValue tv : in.getTaggedValue()) {
            String additionalTag = tv.getTag();
            if (tv.getValue() != null) {
                builder.prop(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES + additionalTag, tv.getValue());
            }
        }

        // Column-specific properties.
        if (in.isKey()) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_IS_KEY, "true"); //$NON-NLS-1$
        }
        if (in.getType() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_SOURCE_TYPE, in.getSourceType());
        }
        if (in.getTalendType() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_TALEND_TYPE, in.getTalendType());
        }
        if (in.getPattern() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_PATTERN, TalendQuoteUtils.removeQuotesIfExist(in.getPattern()));
        }
        if (in.getLength() >= 0) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_LENGTH, String.valueOf((int) in.getLength()));
        }
        if (in.getOriginalLength() >= 0) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_LENGTH, String.valueOf(in.getOriginalLength()));
        }
        if (in.isNullable()) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_IS_NULLABLE, "true"); //$NON-NLS-1$
        }
        if (in.getPrecision() >= 0) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_PRECISION, String.valueOf(in.getPrecision()));
        }
        if (in.getInitialValue() != null && in.getInitialValue().getBody() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_DEFAULT, in.getInitialValue().getBody());
        }
        if (in.getName() != null) {
            // keyword fixes?
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_DB_COLUMN_NAME, in.getName());
        }
        if (in.getRelatedEntity() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_RELATED_ENTITY, in.getRelatedEntity());
        }
        if (in.getRelationshipType() != null) {
            builder.prop(Talend6SchemaConstants.TALEND6_COLUMN_RELATIONSHIP_TYPE, in.getRelationshipType());
        }
        return builder;
    }

    /**
     * @param in A schema which must be of type {@link Schema.Type#RECORD}.
     * @return A MetadataTable containing all the information from the Schema, including any information included the
     * schema as JSON property annotations for Talend 6 generated schemas.
     */
    public static MetadataTable convertFromAvro(org.apache.avro.Schema in) {
        MetadataTable table = ConnectionFactory.eINSTANCE.createMetadataTable();

        // Set the defaults values to the name (the only information guaranteed to be present in the schema.)
        table.setId(in.getName());
        table.setLabel(in.getName());
        table.setName(in.getName());
        table.setSourceName(in.getName());

        // Extract any properties that have been saved in the enriched schema.

        // Properties common to tables and columns.
        String prop;
        if (null != (prop = in.getProp(Talend6SchemaConstants.TALEND6_ID))) {
            table.setId(prop);
        }
        if (null != (prop = in.getProp(Talend6SchemaConstants.TALEND6_COMMENT))) {
            table.setComment(in.getProp(Talend6SchemaConstants.TALEND6_ID));
        }
        if (null != (prop = in.getProp(Talend6SchemaConstants.TALEND6_LABEL))) {
            table.setLabel(null);
        }
        if (null != (prop = in.getProp(Talend6SchemaConstants.TALEND6_IS_READ_ONLY))) {
            table.setReadOnly(Boolean.parseBoolean(prop));
        }
        for (String key : in.getJsonProps().keySet()) {
            if (key.startsWith(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES)) {
                String originalKey = key.substring(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES.length());
                TaggedValue tv = TaggedValueHelper.createTaggedValue(originalKey, in.getProp(key));
                table.getTaggedValue().add(tv);
            }
        }

        // Table-specific properties.
        if (null != (prop = in.getProp(Talend6SchemaConstants.TALEND6_TABLE_NAME))) {
            table.setName(prop);
        }
        if (null != (prop = in.getProp(Talend6SchemaConstants.TALEND6_TABLE_TYPE))) {
            table.setTableType(prop);
        }

        // Add the columns.
        List<org.talend.core.model.metadata.builder.connection.MetadataColumn> columns = new ArrayList<>(in.getFields().size());
        for (Schema.Field f : in.getFields()) {
            columns.add(convertFromAvro(f));
        }
        boolean isDynamic = AvroUtils.isIncludeAllFields(in);
        if (isDynamic) {
            org.talend.core.model.metadata.builder.connection.MetadataColumn col = convertFromAvroForDynamic(in);
            // get dynamic position
            int dynPosition = Integer.valueOf(in.getProp(Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_POSITION));
            columns.add(dynPosition, col);
        }
        table.getColumns().addAll(columns);
        return table;
    }

    public static org.talend.core.model.metadata.builder.connection.MetadataColumn convertFromAvroForDynamic(Schema schema) {
        org.talend.core.model.metadata.builder.connection.MetadataColumn col = ConnectionFactory.eINSTANCE.createMetadataColumn();
        String prop;
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_ID))) {
            col.setId(prop);
        }

        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_COMMENT))) {
            col.setComment(prop);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_DYNAMIC_COLUMN_NAME))) {
            col.setLabel(prop);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_DYNAMIC_IS_READ_ONLY))) {
            col.setReadOnly(Boolean.parseBoolean(prop));
        }
        for (String key : schema.getJsonProps().keySet()) {
            if (key.startsWith(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES)) {
                String originalKey = key.substring(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES.length());
                TaggedValue tv = TaggedValueHelper.createTaggedValue(originalKey, schema.getProp(key));
                col.getTaggedValue().add(tv);
            }
        }

        // Column-specific properties.
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_IS_KEY))) {
            col.setKey(Boolean.parseBoolean(prop));
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_SOURCE_TYPE))) {
            col.setSourceType(prop);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_TALEND_TYPE))) {
            col.setTalendType(prop);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_PATTERN))) {
            col.setPattern(TalendQuoteUtils.addQuotesIfNotExist(prop));
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_LENGTH))) {
            Long value = Long.parseLong(prop);
            col.setLength(value > 0 ? value : -1);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_LENGTH))) {
            Long value = Long.parseLong(prop);
            col.setOriginalLength(value > 0 ? value : -1);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_IS_NULLABLE))) {
            col.setNullable(Boolean.parseBoolean(prop));
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_PRECISION))) {
            Long value = Long.parseLong(prop);
            col.setPrecision(value > 0 ? value : -1);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_DEFAULT))) {
            col.setDefaultValue(prop);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_DB_COLUMN_NAME))) {
            col.setName(prop);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_RELATED_ENTITY))) {
            col.setRelatedEntity(prop);
        }
        if (null != (prop = schema.getProp(Talend6SchemaConstants.TALEND6_COLUMN_RELATIONSHIP_TYPE))) {
            col.setRelationshipType(prop);
        }

        col.setTalendType("id_Dynamic"); //$NON-NLS-1$
        return col;
    }

    /**
     * @param in A field from an incoming schema
     * @return A MetadataColumn containing all the information from the Schema, including any information included the
     * schema as JSON property annotations for Talend 6 generated schemas.
     */
    public static org.talend.core.model.metadata.builder.connection.MetadataColumn convertFromAvro(Schema.Field field) {
        org.talend.core.model.metadata.builder.connection.MetadataColumn col = ConnectionFactory.eINSTANCE.createMetadataColumn();
        Schema in = field.schema();

        // Set the defaults values to the name (the only information guaranteed to be available in every field).
        col.setId(field.name());
        col.setLabel(field.name());
        col.setName(field.name());
        Schema nonnullable = AvroUtils.unwrapIfNullable(in);
        if (AvroTypes.isSameType(nonnullable, AvroTypes._boolean())) {
            col.setTalendType(JavaTypesManager.BOOLEAN.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._byte())) {
            col.setTalendType(JavaTypesManager.BYTE.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._bytes())) {
            col.setTalendType(JavaTypesManager.BYTE_ARRAY.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._character())) {
            col.setTalendType(JavaTypesManager.CHARACTER.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._date())) {
            col.setTalendType(JavaTypesManager.DATE.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._decimal())) {
            col.setTalendType(JavaTypesManager.BIGDECIMAL.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._double())) {
            col.setTalendType(JavaTypesManager.DOUBLE.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._float())) {
            col.setTalendType(JavaTypesManager.FLOAT.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._int())) {
            col.setTalendType(JavaTypesManager.INTEGER.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._long())) {
            col.setTalendType(JavaTypesManager.LONG.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._short())) {
            col.setTalendType(JavaTypesManager.SHORT.getId());
        } else if (AvroTypes.isSameType(nonnullable, AvroTypes._string())) {
            col.setTalendType(JavaTypesManager.STRING.getId());
        }
        // FIXME missing List and Object here

        // TODO setSourceType from the field Schema type.
        col.setNullable(AvroUtils.isNullable(in));

        // Extract any properties that have been saved in the enriched schema.

        // Properties common to tables and columns.
        String prop;
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_ID))) {
            col.setId(prop);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COMMENT))) {
            col.setComment(field.getProp(Talend6SchemaConstants.TALEND6_ID));
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_LABEL))) {
            col.setLabel(null);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_IS_READ_ONLY))) {
            col.setReadOnly(Boolean.parseBoolean(prop));
        }
        for (String key : field.getJsonProps().keySet()) {
            if (key.startsWith(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES)) {
                String originalKey = key.substring(Talend6SchemaConstants.TALEND6_ADDITIONAL_PROPERTIES.length());
                TaggedValue tv = TaggedValueHelper.createTaggedValue(originalKey, field.getProp(key));
                col.getTaggedValue().add(tv);
            }
        }

        // Column-specific properties.
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_IS_KEY))) {
            col.setKey(Boolean.parseBoolean(prop));
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_SOURCE_TYPE))) {
            col.setSourceType(prop);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_TALEND_TYPE))) {
            col.setTalendType(prop);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_PATTERN))) {
            col.setPattern(TalendQuoteUtils.addQuotesIfNotExist(prop));
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_LENGTH))) {
            Long value = Long.parseLong(prop);
            col.setLength(value > 0 ? value : -1);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_LENGTH))) {
            Long value = Long.parseLong(prop);
            col.setOriginalLength(value > 0 ? value : -1);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_IS_NULLABLE))) {
            col.setNullable(Boolean.parseBoolean(prop));
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_PRECISION))) {
            Long value = Long.parseLong(prop);
            col.setPrecision(value > 0 ? value : -1);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_DEFAULT))) {
            col.setDefaultValue(prop);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_ORIGINAL_DB_COLUMN_NAME))) {
            col.setName(prop);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_RELATED_ENTITY))) {
            col.setRelatedEntity(prop);
        }
        if (null != (prop = field.getProp(Talend6SchemaConstants.TALEND6_COLUMN_RELATIONSHIP_TYPE))) {
            col.setRelationshipType(prop);
        }

        // If the source type wasn't set, there is an issue. Can this occur in the studio.
        if (col.getTalendType() == null) {
            throw new UnsupportedOperationException("Unrecognized type " + in); //$NON-NLS-1$
        }

        return col;
    }
}
