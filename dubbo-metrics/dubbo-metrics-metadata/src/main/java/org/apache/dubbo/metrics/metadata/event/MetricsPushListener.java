/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.metrics.metadata.event;

import org.apache.dubbo.metrics.event.MetricsEvent;
import org.apache.dubbo.metrics.listener.MetricsLifeListener;

import static org.apache.dubbo.metrics.metadata.collector.stat.MetadataStatComposite.OP_TYPE_PUSH;

public class MetricsPushListener implements MetricsLifeListener<MetadataEvent.PushEvent> {


    @Override
    public boolean isSupport(MetricsEvent event) {
        return event instanceof MetadataEvent.PushEvent && ((MetadataEvent) event).isAvailable();
    }

    @Override
    public void onEvent(MetadataEvent.PushEvent event) {
        event.getCollector().increment(event.getSource().getApplicationName(), MetadataEvent.ApplicationType.P_TOTAL);
    }

    @Override
    public void onEventFinish(MetadataEvent.PushEvent event) {
        event.getCollector().increment(event.getSource().getApplicationName(), MetadataEvent.ApplicationType.P_SUCCEED);
        event.getCollector().addApplicationRT(event.getSource().getApplicationName(), OP_TYPE_PUSH, event.getTimePair().calc());
    }

    @Override
    public void onEventError(MetadataEvent.PushEvent event) {
        event.getCollector().increment(event.getSource().getApplicationName(), MetadataEvent.ApplicationType.P_FAILED);
        event.getCollector().addApplicationRT(event.getSource().getApplicationName(), OP_TYPE_PUSH, event.getTimePair().calc());
    }
}
