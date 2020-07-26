# Universal API-Tester

The Universal API-Tester should once be a tool for automated (blackbox) testing of a up and running system.
The system, name it a _Microservice_, should be tested without any dependencies to other Systems.


## Background

The Idea to this Project came when our company decided to use gRPC and ProtoBuf over REST for new microservice kind of applications.
Without any thoughts on testing - manually and automated. Meanwhile there are some tools out in the wild for manual testing, but as far as we know, only less (or none) for automated testing.


## The Idea

There is a definition of what we want to send in to the system and define what our expectations are. The Universal API-Tester will start up any defined _Sub-Systems_ as mocks, starts the _Microservice_ itself and fires afterwards requests to it.

* *Listeners:* Since a _Microservice_ can depend on other systems - it should not, but which one does not - these have to be defined in the testcase where we define what data have to be returned. This Subsystems may be for authentication, authorisation or any other kind of systems.
* *Systems:* These are the real systems which are started up. Normally it should be only one, the system we want to test. But it can also be any other System to which you want to have _physical_ access.
* *Requests:* This are the requests which are sent to the system in order. Each Request should define an expected response(?).


# Implementation

The different systems, listeners and requests (others may follow) are defined in Shared Libraries which are loaded by request. The typed Plugin-System idea came from http://blog.nuclex-games.com/tutorials/cxx/plugin-architecture/ which has the advantages that no casting has to be done.

## Configuration

The Configuration will be don in JSON so the system ma run as a CGI and used via existing tools for web testing...


## Libraries

* https://github.com/Tencent/rapidjson


# Other stuff

* https://curl.haxx.se/libcurl/c/example.html

