# neo4j-ogm-repository-rest-resource

To demonstrate `NullPointerException` described in https://github.com/neo4j/neo4j-ogm/issues/781


## Prerequisite

Running neo4j instance on localhost
```
docker run \
    --publish=7474:7474 --publish=7687:7687 \
    --volume=$HOME/neo4j/data:/data \
    neo4j:4.0.0
```


## Steps to reproduced

`POST` a new `Unit` referencing a relationship to non-existing `Team`
```
curl --location --request POST 'http://localhost:8080/v1/units/' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
  "unitName": "unit1",
  "ownedBy": [
    "http://localhost:8080/v1/teams/123"
  ]
}'
```

Causes `java.lang.NullPointerException` with stacktrace

```
java.lang.NullPointerException: null
	at org.neo4j.ogm.metadata.MetaData.classInfo(MetaData.java:123) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at org.neo4j.ogm.context.EntityGraphMapper.bothWayMappingRequired(EntityGraphMapper.java:996) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at org.neo4j.ogm.context.EntityGraphMapper.mapEntityReferences(EntityGraphMapper.java:419) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at org.neo4j.ogm.context.EntityGraphMapper.mapEntity(EntityGraphMapper.java:276) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at org.neo4j.ogm.context.EntityGraphMapper.map(EntityGraphMapper.java:164) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at org.neo4j.ogm.session.delegates.SaveDelegate.lambda$save$1(SaveDelegate.java:89) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at java.util.Collections$SingletonList.forEach(Collections.java:4822) ~[na:1.8.0_101]
	at org.neo4j.ogm.session.delegates.SaveDelegate.save(SaveDelegate.java:89) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at org.neo4j.ogm.session.delegates.SaveDelegate.save(SaveDelegate.java:51) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at org.neo4j.ogm.session.Neo4jSession.save(Neo4jSession.java:480) ~[neo4j-ogm-core-3.2.9.jar:3.2.9]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_101]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_101]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_101]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_101]
	at org.springframework.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:282) ~[spring-core-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.data.neo4j.transaction.SharedSessionCreator$SharedSessionInvocationHandler.lambda$invoke$1(SharedSessionCreator.java:121) ~[spring-data-neo4j-5.2.5.RELEASE.jar:5.2.5.RELEASE]
	at org.springframework.data.neo4j.transaction.SharedSessionCreator$SharedSessionInvocationHandler.invokeInTransaction(SharedSessionCreator.java:159) ~[spring-data-neo4j-5.2.5.RELEASE.jar:5.2.5.RELEASE]
	at org.springframework.data.neo4j.transaction.SharedSessionCreator$SharedSessionInvocationHandler.invoke(SharedSessionCreator.java:123) ~[spring-data-neo4j-5.2.5.RELEASE.jar:5.2.5.RELEASE]
	at com.sun.proxy.$Proxy81.save(Unknown Source) ~[na:na]
	at org.springframework.data.neo4j.repository.support.SimpleNeo4jRepository.save(SimpleNeo4jRepository.java:74) ~[spring-data-neo4j-5.2.5.RELEASE.jar:5.2.5.RELEASE]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_101]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_101]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_101]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_101]
	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:371) ~[spring-data-commons-2.2.5.RELEASE.jar:2.2.5.RELEASE]
	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:204) ~[spring-data-commons-2.2.5.RELEASE.jar:2.2.5.RELEASE]
	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:657) ~[spring-data-commons-2.2.5.RELEASE.jar:2.2.5.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.data.repository.core.support.RepositoryFactorySupport$QueryExecutorMethodInterceptor.doInvoke(RepositoryFactorySupport.java:621) ~[spring-data-commons-2.2.5.RELEASE.jar:2.2.5.RELEASE]
	at org.springframework.data.repository.core.support.RepositoryFactorySupport$QueryExecutorMethodInterceptor.invoke(RepositoryFactorySupport.java:605) ~[spring-data-commons-2.2.5.RELEASE.jar:2.2.5.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366) ~[spring-tx-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99) ~[spring-tx-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:139) ~[spring-tx-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212) ~[spring-aop-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at com.sun.proxy.$Proxy88.save(Unknown Source) ~[na:na]
	at org.springframework.data.repository.support.CrudRepositoryInvoker.invokeSave(CrudRepositoryInvoker.java:101) ~[spring-data-commons-2.2.5.RELEASE.jar:2.2.5.RELEASE]
	at org.springframework.data.rest.core.support.UnwrappingRepositoryInvokerFactory$UnwrappingRepositoryInvoker.invokeSave(UnwrappingRepositoryInvokerFactory.java:176) ~[spring-data-rest-core-3.2.5.RELEASE.jar:3.2.5.RELEASE]
	at org.springframework.data.rest.webmvc.RepositoryEntityController.createAndReturn(RepositoryEntityController.java:478) ~[spring-data-rest-webmvc-3.2.5.RELEASE.jar:3.2.5.RELEASE]
	at org.springframework.data.rest.webmvc.RepositoryEntityController.postCollectionResource(RepositoryEntityController.java:270) ~[spring-data-rest-webmvc-3.2.5.RELEASE.jar:3.2.5.RELEASE]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_101]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_101]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_101]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_101]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190) ~[spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138) ~[spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106) ~[spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:879) ~[spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:793) ~[spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040) ~[spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943) ~[spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) [spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909) [spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:660) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) [spring-webmvc-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:741) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) [tomcat-embed-websocket-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) [spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) [spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) [spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) [spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) [spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) [spring-web-5.2.4.RELEASE.jar:5.2.4.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:541) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:367) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:868) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1639) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142) [na:1.8.0_101]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617) [na:1.8.0_101]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-9.0.31.jar:9.0.31]
	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_101]
```


### Notice - happy flow works OK

If you `POST` a new `Unit` referencing existing `Team`, it correctly returns `201 CREATED`.
