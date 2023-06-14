# Space Game Map
The map works by having node objects (planets) which contain a list of the planets they are connected to. So for each planet, it has like 2-3 planets it's connected to. 

## Generating Planets
Each planet on creation will have a bunch of randomly set values, each pertaining to things found on that planet. 

## Generating Systems
Each system will be generated to have a global race, and how much economy it has, which influences the amount of resources found on each planet.
One system will be marked as the center of the galaxy, and will have no planets in it. 

### Planet Types - Not Implemented
- Industrial
Has some goods of value on it, or a bunch of units. a few settlements, that each have some units, or an artifact of value or something. 

- TBD

## Generating the Map
The idea is to generate a ton of systems, loop over them all, and randomly connect them. This approach would make a sort of 3D map, but could result in a system that is connected across the galaxy. I feel this could be remedied with a `distanceFromStart` variable of sorts that measures out how far the planets are. For now tho, just random assignment works.   

## Rendering the Map
I render the map by drawing a large V shape, and then putting the current system name, at the bottom of the V. The two arms of the V show the systems that you can warp to. 
