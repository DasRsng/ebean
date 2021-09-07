package org.tests.transaction;

import io.ebean.BaseTestCase;
import io.ebean.Ebean;
import io.ebeaninternal.api.SpiTransaction;
import org.tests.model.basic.EBasicVer;
import org.tests.model.basic.ResetBasicData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestDeleteFromPersistenceContext extends BaseTestCase {

  @Test
  public void testDeleteBean() {

    ResetBasicData.reset();

    EBasicVer bean = new EBasicVer("Please Delete Me");
    Ebean.save(bean);

    SpiTransaction transaction = (SpiTransaction) Ebean.beginTransaction();
    try {

      EBasicVer bean2 = Ebean.find(EBasicVer.class, bean.getId());
      assertNotSame(bean, bean2);

      EBasicVer bean3 = Ebean.find(EBasicVer.class, bean.getId());
      // same instance from PersistenceContext
      assertSame(bean2, bean3);

      Object bean4 = transaction.getPersistenceContext().get(EBasicVer.class, bean.getId());
      assertSame(bean2, bean4);

      Ebean.delete(bean2);

      Object bean5 = transaction.getPersistenceContext().get(EBasicVer.class, bean.getId());
      assertNull(bean5);

      Ebean.commitTransaction();

    } finally {
      Ebean.endTransaction();
    }

    EBasicVer bean6 = Ebean.find(EBasicVer.class).where().eq("id", bean.getId()).findOne();
    assertNull(bean6);

    awaitL2Cache();
    EBasicVer bean7 = Ebean.find(EBasicVer.class, bean.getId());
    assertNull(bean7);
  }

}
