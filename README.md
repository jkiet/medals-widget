# medals-widget

## Dependencies

- `java 8 JDK` either oracle or openjdk

## Building

    $ lein uberjar

## Packaging

    $ cp target/uberjar/medals-widget-0.1.0-SNAPSHOT-standalone.jar release/

## Running standalone app

    $ java -jar release/medals-widget-0.1.0-SNAPSHOT-standalone.jar


- standalone dev server should start listening on port 8080;
- the server sometimes intentionally returns 404,500,503 on data route.

## Usage

    $ http://localhost:8080/?element_id=<foo|bar|baz>&sort=<whatever>


### Bugs

... undisclosed yet

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
