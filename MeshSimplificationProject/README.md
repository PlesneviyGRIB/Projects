# Team project of Mesh Simplification.

The idea of the project was to write various algorithms for simplification heavy 3D models.


### Mesh-Simplification Library:

1) Algorithms library -> implementation of algorithms and thiers inner realization.
2) Input Output library -> implementation of reading, writing and converting files.
3) Visualization package -> program that shows 3d models


### Usage:
           
    Model model = new ImporterPly().Import("./models/bunny.ply");

    Algorithm algorithm = new FastVertexCollapsingInRadius(model);

    new ExporterPly().Export("./out/bunny_simplified.ply", algorithm.GetSimplifiedModel(), false);


### Authors:

	https://github.com/EresK
	https://github.com/sequut
	https://github.com/Ingvar-fesh
	https://github.com/PlesneviyGRIB


### Repository where development took place

	https://github.com/EresK/Mesh-Simplification