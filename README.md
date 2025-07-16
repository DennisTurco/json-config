# JsonConfig

**JsonConfig** is a utility class designed to load and flatten JSON configuration files into a simple key-value map. It parses a JSON file and transforms nested structures into a single-level HashMap using concatenated keys (e.g., "MenuItems_BugReport"). This makes accessing deeply nested configuration values straightforward and efficient.

It supports reloading configurations at runtime and retrieving individual values by key.

## Key Features
- Load configuration data from a JSON file.
- Flatten nested JSON objects into a single-level key-value map.
- Access configuration values using string keys.
- Reload configuration file on demand.
- Useful for managing application settings or UI content in JSON format.

## Installation
If you are using a **Maven** project, you can add this dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>com.github.DennisTurco</groupId>
    <artifactId>JsonConfig</artifactId>
    <version>v1.0.0</version>
</dependency>
```
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

## Usage
📘 [Javadoc Documentation](https://dennisturco.github.io/JsonConfig/com/dennisturco/JsonConfig/package-summary.html)


### Basic Example

```json
{
  "APP_NAME": "test",
  "LOGO_IMG": "/res/img/logo.png",
  "VERSION": "1.0.0",

  "MenuItems": {
    "New": true,
    "Quit": true,
    "Save": true
  },
  "File": {
    "Readme": {
        "path": "a_general_path",
        "extension": ".md"
    },
    "Properties": {
        "path": "another_general_path",
        "extension": ".config"
    }
  }
}
```

```java
import com.dennisturco.JsonConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        JsonConfig configs = new JsonConfig("src/main/resources/config.json");

        System.out.println(configs.getConfigByKey("APP_NAME"));
        System.out.println(configs.getConfigByKey("MenuItems_New"));
        System.out.println(configs.getConfigByKey("File_Readme_extension"));

        // for test
        System.out.println("\n---------------------------------------");
        configs.printAllConfigs();
    }
}
```
Thanks to **JsonConfig**, you can access your configuration values as follows:
```txt
--- All Flattened Configs ---
APP_NAME: test
LOGO_IMG: /res/img/logo.png
VERSION: 1.0.0
MenuItems_New: true
MenuItems_Quit: true
MenuItems_Save: true
File_Readme_path: a_general_path
File_Readme_extension: .md
File_Properties_path: another_general_path
File_Properties_extension: .config
```

## License

This utility is provided under the MIT License.