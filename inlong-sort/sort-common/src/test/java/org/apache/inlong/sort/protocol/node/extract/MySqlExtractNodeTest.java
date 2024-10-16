/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.sort.protocol.node.extract;

import org.apache.inlong.common.enums.MetaField;
import org.apache.inlong.sort.SerializeBaseTest;
import org.apache.inlong.sort.formats.common.StringFormatInfo;
import org.apache.inlong.sort.protocol.FieldInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Test for {@link MySqlExtractNode}
 */
public class MySqlExtractNodeTest extends SerializeBaseTest<MySqlExtractNode> {

    @Override
    public MySqlExtractNode getTestObject() {
        return new MySqlExtractNode("1", null,
                Collections.singletonList(new FieldInfo("field", new StringFormatInfo())), null, null,
                "primary_key_field", Arrays.asList("table1", "table2"),
                "localhost", "username",
                "password", "dabasename", 3306, 123,
                true, null);
    }

    @Test
    public void testMetaFields() {
        Map<MetaField, String> formatMap = new HashMap<>();
        formatMap.put(MetaField.PROCESS_TIME, "AS PROCTIME()");
        formatMap.put(MetaField.TABLE_NAME, "STRING METADATA FROM 'meta.table_name' VIRTUAL");
        formatMap.put(MetaField.DATA, "STRING METADATA FROM 'meta.data' VIRTUAL");
        formatMap.put(MetaField.DATABASE_NAME, "STRING METADATA FROM 'meta.database_name' VIRTUAL");
        formatMap.put(MetaField.OP_TYPE, "STRING METADATA FROM 'meta.op_type' VIRTUAL");
        formatMap.put(MetaField.OP_TS, "TIMESTAMP_LTZ(3) METADATA FROM 'meta.op_ts' VIRTUAL");
        formatMap.put(MetaField.IS_DDL, "BOOLEAN METADATA FROM 'meta.is_ddl' VIRTUAL");
        formatMap.put(MetaField.TS, "TIMESTAMP_LTZ(3) METADATA FROM 'meta.ts' VIRTUAL");
        formatMap.put(MetaField.SQL_TYPE, "MAP<STRING, INT> METADATA FROM 'meta.sql_type' VIRTUAL");
        formatMap.put(MetaField.MYSQL_TYPE, "MAP<STRING, STRING> METADATA FROM 'meta.mysql_type' VIRTUAL");
        formatMap.put(MetaField.PK_NAMES, "ARRAY<STRING> METADATA FROM 'meta.pk_names' VIRTUAL");
        formatMap.put(MetaField.BATCH_ID, "BIGINT METADATA FROM 'meta.batch_id' VIRTUAL");
        formatMap.put(MetaField.UPDATE_BEFORE, "ARRAY<MAP<STRING, STRING>> METADATA FROM 'meta.update_before' VIRTUAL");
        formatMap.put(MetaField.DATA_BYTES, "BYTES METADATA FROM 'meta.data' VIRTUAL");
        MySqlExtractNode node = getTestObject();
        boolean formatEquals = true;
        for (MetaField metaField : node.supportedMetaFields()) {
            formatEquals = node.format(metaField).equals(formatMap.get(metaField));
            if (!formatEquals) {
                break;
            }
        }
        Assert.assertTrue(formatEquals);
    }
}
