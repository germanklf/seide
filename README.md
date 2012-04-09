= What is Seide? =

Seide Framework is a reference implementation intended to provide high performance multi threaded processing based on SEDA principles. *Highly inspired by the Cassandra*'s implementation and proved performance, scalability and robustness.

Each step of the workflow is defined by a Stage and implemented by an EventHandler, each Event defined is tied to an EventHandler, that does the real work and decides what are the next events fired from there.

*Sounds simple, it's simple.*

It provides a Spring-friendly support artifact to make the Spring configuration easier and exposes JMX performance statistics of internal processes, making easy to see where is the bottleneck in your application.

This project is in use in production on a high traffic site, hosting an asynchronous remote file tracking & audit service, and a Content Rendering Service.

References: 

http://www.eecs.harvard.edu/~mdw/proj/seda/

http://www.eecs.harvard.edu/~mdw/papers/seda-sosp01.pdf

http://cassandra.apache.org/

== Release `r02` ==
This release added a few refactors to easily extend the base functionality. It's already on maven central.

Maven:
{{{
    <dependency>
        <groupId>net.sf.seideframework</groupId>
        <artifactId>seide-core</artifactId>
        <version>r02</version>
    </dependency>
}}}

Core:

jar: https://oss.sonatype.org/content/repositories/releases/net/sf/seideframework/seide-core/r02/seide-core-r02.jar

sources: https://oss.sonatype.org/content/repositories/releases/net/sf/seideframework/seide-core/r02/seide-core-r02-sources.jar

Spring3:

jar: https://oss.sonatype.org/content/repositories/releases/net/sf/seideframework/seide-spring3/r02/seide-spring3-r02.jar

sources: https://oss.sonatype.org/content/repositories/releases/net/sf/seideframework/seide-spring3/r02/seide-spring3-r02-sources.jar

Artifacts are published in Sonatype repositories (Snapshots & Releases soon).

Using the following lines in your pom.xml you can add dependency.
{{{
    <dependency>
        <groupId>net.sf.seideframework</groupId>
        <artifactId>seide-core</artifactId>
        <version>r02</version>
    </dependency>
}}}

And the spring3 dependency:
{{{
    <dependency>
        <groupId>net.sf.seideframework</groupId>
        <artifactId>seide-spring3</artifactId>
        <version>r02</version>
    </dependency>
}}}

Update: to avoid misinterpretation of an 1.X version value or content, I've decided to change the version style to rXX. The next version will be "`r01`", because it's an acceptable first version, and usable in production environments.

== Dependencies ==
There are no dependencies needed to use Seide core, I use junit just in test scope but it's only needed if you're trying checkout and play around with the source code.

The only dependency needed to spring3 support is spring-context 3.0.5.RELEASE. I used that version because it's the latest, It may work with 2.5+ either but I didn't test it.
