# Spring boot application and Opentracing Integration POC
## Small POC Spring boot wth opentracing application 

This is a demo application to show:
1) using opentracing-contrib library for integeration to minimize the work
2) since opentracing is vendor neutral, we would like to hook up with one vendor. Lightstep for our case.

Libraries looked at:
1) https://github.com/opentracing-contrib/java-spring-web
Good for simeple *just* ingress and egress. 

2) https://github.com/opentracing-contrib/java-spring-cloud
Good for ingress and egress as well as trace controllers. Also includes JVm instrumentation.

3) spring-cloud/spring-cloud-sleuth
Same as 2, except older but depends on Brave Tracer as proxy.

To Run:
just set the following as your JVM Env variables
LIGHTSTEP_ENABLED=true;LIGHTSTEP_ACCESS_TOKEN=<your valid access token>;LIGHTSTEP_VERBOSE=4


Notes.
* Opentracing API is mostly a set of abstract class and interfaces, they outsource as mucha s the concrete stuff to vendors/tracer implementation.
* Opentracing library will try to resolve a tracer from Globaltracer
* To protect a bad initialization of tracer, a NoopTracer is set to global tracer
* For http requests coming to server, there are 2 cases. The requests originates from a source that created a span,
and the server will creates a child span; The incoming request is form sources like curl, the server will create a new span.
* For http client (apache http client), it will be nice to just configure an interceptor that automatically creates a span for outgoing requests.
* Kinda makes the span operation name, taging conventions more inline for rest of SCP services.
Sample link:
https://app.lightstep.com/splunk-dev/trace?span_guid=61d7c87c0e95a0a2&at_micros=1544703334919160#span-61d7c87c0e95a0a2   

Resources:
* http://opentracing.io/documentation/pages/spec
* https://github.com/opentracing/specification/blob/master/semantic_conventions.md
* https://cd.splunkdev.com/libraries/go-observation/tree/SSC-2176-Distributed-tracing-with-open-tracing-cleaned-up-version-lightstep-integeration
* https://github.com/opentracing/opentracing-go

* https://github.com/lightstep/lightstep-tracer-java
* https://github.com/lightstep/lightstep-tracer-java-common

REPO:
* Location: 
  https://github.com/etsangsplk/opentracing-java-examples
* Location + branch:
  https://github.com/etsangsplk/opentracing-java-examples/tree/lightstep`