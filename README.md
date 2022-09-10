# yaml2env

![Build](https://github.com/gamachec/yaml2env/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

<!-- Plugin description -->
<p>Adds a context menu to transform a Yaml property file into a key=value list that can be used with the Docker command line or a Kubernetes configmap.</p>
<p><a href="https://github.com/gamachec/yaml2env">https://github.com/gamachec/yaml2env</a></p>
<p>Yaml converter use Spring Boot relaxing binding to transform the keys :</p>
<ul>
    <li>Replace dots (.) with underscores (_)</li>
    <li>Remove any dashes (-)</li>
    <li>Convert to uppercase</li>
</ul>
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "yaml2env"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/gamachec/yaml2env/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
