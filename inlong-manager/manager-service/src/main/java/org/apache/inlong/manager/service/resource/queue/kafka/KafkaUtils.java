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

package org.apache.inlong.manager.service.resource.queue.kafka;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.apache.inlong.manager.pojo.cluster.kafka.KafkaClusterInfo;
import org.apache.inlong.manager.pojo.group.kafka.InlongKafkaInfo;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * kafka connection utils
 */
@Slf4j
public class KafkaUtils {

    public static AdminClient getAdminClient(KafkaClusterInfo kafkaClusterInfo) {
        Properties properties = new Properties();
        // Configure the access address and port number of the Kafka service
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterInfo.getUrl());
        // Create AdminClient instance
        return AdminClient.create(properties);
    }

    public static KafkaConsumer createKafkaConsumer(InlongKafkaInfo inlongKafkaInfo,KafkaClusterInfo kafkaClusterInfo) {
        Properties properties = new Properties();
        // The connected kafka cluster address
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterInfo.getUrl());
        // consumer grouping
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, inlongKafkaInfo.getGroupId());
        // Confirm Auto Commit
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // autocommit interval
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // Serialization
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        // For different groupid to ensure that the previous message can be consumed, reset the offset
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaConsumer(properties);
    }
}
