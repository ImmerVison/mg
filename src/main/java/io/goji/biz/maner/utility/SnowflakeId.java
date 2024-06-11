package io.goji.biz.maner.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class SnowflakeId {
    private long epoch = 1609459200000L; //2021-01-01 00:00:00
    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = ~(-1L << workerIdBits);
    private long maxDatacenterId = ~(-1L << datacenterIdBits);
    private long sequenceBits = 12L;
    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = ~(-1L << sequenceBits);
    private long workerId;
    private long datacenterId;

    public String generateId(long datacenterId) {
        return String.valueOf(this.nextId(datacenterId));
    }

    public synchronized long nextId(long datacenterId) {
        long timestamp = System.currentTimeMillis();
        if (timestamp < epoch) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than " + maxDatacenterId + " or less than 0");
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than " + maxWorkerId + " or less than 0");
        }
        long sequence = 0L;
        if (timestamp == epoch) {
            sequence = sequence + 1 & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(timestamp);
            }
        } else {
            sequence = 0L;
        }
        return timestamp - epoch << timestampLeftShift | datacenterId << datacenterIdShift | workerId << workerIdShift | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public SnowflakeId(long workerId, long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public SnowflakeId() {
        this.workerId = 0;
        this.datacenterId = 0;
    }

    public SnowflakeId(long workerId) {
        this.workerId = workerId;
        this.datacenterId = 0;
    }

    public SnowflakeId(long workerId, long datacenterId, long epoch) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.epoch = epoch;
    }



}
