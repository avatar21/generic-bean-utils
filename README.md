## Generic Bean Utilities

![maven build status badge](https://github.com/avatar21/generic-bean-utils/actions/workflows/maven.yml/badge.svg)

Generic Java bean utilities

Here's are some utility usage ...

### GenericBeanUtils usage

Collection of generic bean utility functions

```java
Double mDouble = (Double) GenericBeanUtils.parseStringToGenericType(Double.class, "1.2");
SexEnum sex = (SexEnum) GenericBeanUtils.parseStringToGenericType(SexEnum.class, "MALE");
Boolean mBoolean = (Boolean) GenericBeanUtils.parseStringToGenericType(Boolean.class, "0");
Boolean mBoolean2 = (Boolean) GenericBeanUtils.parseStringToGenericType(Boolean.class, "true");
```

### CalendarUtils usage

Calendar/ Date related functions

```java
Date startDate = GenericBeanUtils.genericParseDate("2002-03-28");
Date endDate = GenericBeanUtils.genericParseDate("2002-03-31");
boolean isSameMonth = CalendarUtils.isSameMonth(startDate, endDate);
TimeDuration duration = CalendarUtils.calculateTimeDuration(startDate, endDate);
System.out.println(String.format("time duration = %s", GenericBeanUtils.toJson(duration)));
```
