package com.cleo.cis.server.kafka.sample.consumer;

import com.cleo.cis.server.mongo.sample.EventRepo;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Created by santoshsingh on 04/01/17.
 */
public class ConsumerTest implements Runnable {
  private KafkaStream m_stream;
  private int m_threadNumber;
  private EventRepo eventRepo;

  public ConsumerTest(KafkaStream a_stream, int a_threadNumber, EventRepo eventRepo) {
    m_threadNumber = a_threadNumber;
    m_stream = a_stream;
    this.eventRepo = eventRepo;
  }

  public void run() {
    ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
    while (it.hasNext()){
      //System.out.println("Thread " + m_threadNumber + ": " + new String(it.next().message()));
      System.out.println("calling Repo Insert..");
      eventRepo.insertEvent(new String(it.next().message()));
    }
    System.out.println("Shutting down Thread: " + m_threadNumber);
  }
}
