# space-exploration
Made with Java, as my final Comp Sci project.

## What is this?
`space-exploration` is a multiplayer space exploration, and capitalist style video game. Think of it as Adventure Capitalist, except the adventure is in space, and the only capitalism is selling things and ransacking planets.
## How does it work?
For a detailed explination on how each class works, please refer to [Code Documentation](docs/code.md)
## How can I use it?
Download the latest release from the releases section on the right, and run it with `java -jar space.jar`
You will require a server to be hosted for this to work, so either get the IP address and port of a pre-hosted one, or host your own with `java -jar space.jar --server`

### Local Testing (localhost)
To host the server: `java -jar space.jar --dev`

To run the client: `java -jar space.jar --dev-client`

These commands will automatically connect the server to the client, and will work out of the box. 

## I wanna inspect the code! You didn't send me a .zip in the D2L dropbox!
Yes i know, simply download the entire repository by clicking on the `Code<>` button, and then download as zip button in the corner.

## Other Docs
[Map Generation](docs/map.md)

[Websocket Communication](docs/websockets.md)

[Dev Log](docs/dev.md)

[Reflection](doc/reflection.md)
