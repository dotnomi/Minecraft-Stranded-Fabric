package com.dotnomi.stranded.event;

public class TestHandler implements ConfigChangedEvent.ClientConfigChanged {
  @Override
  public void onClientConfigChanged(String modid, String fieldName, Object oldValue, Object newValue) {

  }
}
