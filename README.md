# ForMat-Intellij-Plugin

Branch | CI status
--------|----------
Master | [![Build Status](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin.svg?branch=master)](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin)
Develop | [![Build Status](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin.svg?branch=develop)](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin)


## Main idea
В основе лежит идея автоматического форматирования кода, выбрав **файл**, **кусок кода** или отформатировать разницу между коммитами, получив её через команду **git diff**. Будут добавлены возможности добавления собственных параметров форматирования, но в основном плагин будет заточен под язык **Java** в **"Intelij Idea"**.
Возможна адаптация под другие языки и под другие среды разработки, возможно не принадлежащие компании Jet Brains.
## Changes
Будет использован уже существующий фреймворк для форматирования кода Google-java-format. Будет реализован фронтенд для взаимодействия этого фреймворка с Intelij-Idea через её API.
