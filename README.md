# Demo of OpenAPI validation in Spring Boot

Demonstration of dynamic OpenAPI validation using JSR-303 and Spring Boot. This application contains a backend
application with REST API for managing Documents with JSON data and a Single Page App that delivers a GUI
for the REST API. This setup simulises a much encountered scenario. 

[![Build Status](https://travis-ci.org/boonen/oas-validation-demo.svg?branch=master)](https://travis-ci.org/boonen/oas-validation-demo)
[![Docker Repository][dockerhub-shield]][dockerhub-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
[![Twitter][twitter-shield]][twitter-url]

## Getting started

The project is based on [Spring Boot](https://spring.io/projects/spring-boot) 2.3.x and [React Admin](https://marmelab.com/react-admin/)
3.8.x. The backend is written in Java and the frontend is written in JavaScript. Both applications are packed
in a *fat JAR* and run on the same server (and port).

### Prerequisites

To build the project you need the following sofware:
* Java JDK (11 or higher)
* Maven 3

To run the project you need:
* Docker

### Installation

It's easiest to run the application using Docker. 

1. Run using Docker
```sh
docker run --name oas-validation-demo -d -p 8080:8080 boonen/oas-validation-demo:latest
```

### Building from source

1. Clone the repo
```sh
git clone https://github.com/boonen/oas-validation-demo.git
```
2. Build using maven
```sh
mvn spring-boot:build-image
```

## Usage

Now open your browser and point to <http://localhost:8080/files/index.html> for the GUI or <http://localhost:8080/files/apis/locations.yaml> 
for the OpenAPI v3 definitions.

## Roadmap

See the [open issues](https://github.com/boonen/oas-validation-demo/issues) for a list of proposed features (and known issues).


## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## License

Distributed under the MIT License. See `LICENSE` for more information.


## Contact

Jan Boonen - [@boonen](https://twitter.com/boonen) - jan.boonen [at] geodan.nl

Project Link: [https://github.com/boonen/oas-validation-demo](https://github.com/boonen/oas-validation-demo)

## Acknowledgements
* [Img Shields](https://shields.io)
* [Choose an Open Source License](https://choosealicense.com)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/boonen/oas-validation-demo.svg?style=flat-square
[contributors-url]: https://github.com/boonen/oas-validation-demo/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/boonen/oas-validation-demo.svg?style=flat-square
[forks-url]: https://github.com/boonen/oas-validation-demo/network/members
[stars-shield]: https://img.shields.io/github/stars/boonen/oas-validation-demo.svg?style=flat-square
[stars-url]: https://github.com/boonen/oas-validation-demo/stargazers
[issues-shield]: https://img.shields.io/github/issues/boonen/oas-validation-demo.svg?style=flat-square
[issues-url]: https://github.com/boonen/oas-validation-demo/issues
[license-shield]: https://img.shields.io/github/license/boonen/oas-validation-demo.svg?style=flat-square
[license-url]: https://github.com/boonen/oas-validation-demo/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/boonen
[twitter-shield]: https://img.shields.io/badge/-Twitter-blue.svg?style=flat-square&logo=twitter&colorB=555
[twitter-url]: https://twitter.com/boonen
[dockerhub-shield]: https://img.shields.io/badge/-Docker-blue.svg?style=flat-square&logo=docker&colorB=555
[dockerhub-url]: https://hub.docker.com/r/boonen/oas-validation-demo