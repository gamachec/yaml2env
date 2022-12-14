# Yaml2env IntelliJ Platform Plugin

![Build](https://github.com/gamachec/yaml2env/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/19905.svg)](https://plugins.jetbrains.com/plugin/19905)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/19905.svg)](https://plugins.jetbrains.com/plugin/19905)

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

## Usage

Use this plugin to create an envfile from a Spring Boot property file. You can then adapt the content, and use it to test your Docker images locally or using a Kubernetes configmap.

<ul>
<li>Select your application(-profile).yaml</li>
<li>Click on 'Convert Yaml to Env' menu</li>
<li>Paste the content and adapt values to your needs</li>
<li>Save the file or use values on IntelliJ IDEA runner environment option</li>
</ul>

Usage example with docker :
<pre>
docker run --env-file /path/to/myenv.txt <image>
</pre>

Usage example with docker-compose :
<pre>
docker compose --env-file /path/to/myenv.txt up
</pre>

Usage example with k8s configmap :
<pre>
kubectl create configmap my-config-map --from-env-file=/path/to/myenv.txt
</pre>



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
