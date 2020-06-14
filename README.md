# ForMat-Intellij-Plugin

Branch | CI status
--------|----------
Master | [![Build Status](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin.svg?branch=master)](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin)
Develop | [![Build Status](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin.svg?branch=develop)](https://travis-ci.org/DontKnowers/ForMat-Intellij-Plugin)


## Main idea
В основе лежит идея автоматического форматирования кода, выбрав **файл** или **часть кода**
## Реализация
В качестве ядра использован уже существующий фреймворк для форматирования кода **Google-java-format**. Реализован фронтенд для взаимодействия ядра с исполняемой средой Intellij-Idea через её API.
## Будет реализовано в будущем
Возможность отформатировать разницу между коммитами, получив её через команду **git diff**. Будут добавлены возможности добавления собственных параметров форматирования, но в основном плагин будет заточен под язык **Java** в **"Intelij Idea"**.
Возможна адаптация под другие языки и под другие среды разработки, возможно не принадлежащие компании JetBrains.