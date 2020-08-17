This project improves on an earlier project (https://github.com/maineffort/cavas-security-gateway), by directly integrating security testing into a CI/CD pipeline via Jenkins.
The flexible Jenkins plugins ecosystem is exploited to enable docker image testing and application testing for microservices.
Test results are stored in a database for further risk analysis. 

Additionally mattermost server is used for collaboration between devs, ops and sec teams (yet to add this feature to the public repo).

Some inspirations are drawn from - https://wiki.jenkins.io/display/JENKINS/Anchore+Container+Image+Scanner+Plugin

Some of the core ideas behind this project are published in our paper - [CAVAS: Neutralizing Application and Container Security Vulnerabilities in the Cloud Native Era](https://www.researchgate.net/publication/324273101_CAVAS_Neutralizing_Application_and_Container_Security_Vulnerabilities_in_the_Cloud_Native_Era), presented at Securecomm 2018, Singapore.
