# Local Development Setup

This guide gets a full asd4j development environment running on your local machine.

## Prerequisites

| Tool | Version | Notes |
|------|---------|-------|
| Docker Desktop | 4.78+ | Required for Redpanda |
| Java (Oracle JDK) | 25 | Targets Java 17 source compatibility |
| IntelliJ IDEA Community | Latest | Recommended IDE |
| Git | Any | |

## Clone the Repository

```bash
git clone https://github.com/nz-zxc/asd4j
cd asd4j
```

## Build the Project

```bash
./gradlew build
```

## Event Stream Layer (Redpanda)

Redpanda provides the Kafka-compatible event backbone. Two containers are required — the broker and the console UI.

### Start Redpanda Broker

```bash
docker run -d --name redpanda \
  -p 9092:9092 \
  -p 9644:9644 \
  docker.redpanda.com/redpandadata/redpanda:latest \
  redpanda start \
  --overprovisioned \
  --kafka-addr PLAINTEXT://0.0.0.0:9092 \
  --advertise-kafka-addr PLAINTEXT://redpanda:9092
```

### Start Redpanda Console

```bash
docker run -d --name redpanda-console \
  -p 8080:8080 \
  --link redpanda \
  -e KAFKA_BROKERS=redpanda:9092 \
  docker.redpanda.com/redpandadata/console:latest
```

### Verify

Open [http://localhost:8080](http://localhost:8080)

You should see:
- Cluster Status: **Running**
- Brokers Online: **1 of 1**
- Broker 0: **Running**

### Create Development Topics

Via the console UI (Topics → Create topic) or via rpk:

| Topic | Partitions | Replicas | Purpose |
|-------|-----------|---------|---------|
| asd4j.telemetry | 1 | 1 | Temperature, CO2, lux, power readings |
| asd4j.presence | 1 | 1 | BLE, PIR presence events |
| asd4j.occupancy | 1 | 1 | People counter occupancy events |
| asd4j.operational | 1 | 1 | Equipment state changes |
| asd4j.alarms | 1 | 1 | Alarm and fault events |
| asd4j.configuration | 1 | 1 | Component configuration changes |
| asd4j.audit | 1 | 1 | Command and access audit trail |

### Stop Redpanda

```bash
docker stop redpanda redpanda-console
docker rm redpanda redpanda-console
```

### Restart Redpanda (existing containers)

```bash
docker start redpanda
docker start redpanda-console
```

## Notes

### Windows
The `--advertise-kafka-addr PLAINTEXT://redpanda:9092` flag is required on Windows
so the console container can reach the broker container by name rather than
attempting to use localhost (which resolves differently inside Docker).

### Developer mode
The `--overprovisioned` flag enables Redpanda developer mode. This bypasses
production checks for memory and CPU allocation.

> ⚠️ Data may be lost if Docker is stopped uncleanly. This is expected in
> development. Do not use this configuration in production.

### Port reference

| Port | Service | Notes |
|------|---------|-------|
| 9092 | Kafka API | Used by asd4j-coord, asd4j-event, asd4j-insight |
| 9644 | Redpanda Admin API | Cluster health and configuration |
| 8080 | Redpanda Console | Browser UI for topics and messages |

## Architecture

See [architecture.md](../architecture.md) for the full six layer asd4j architecture.