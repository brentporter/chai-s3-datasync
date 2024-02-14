### CHAI S3 Data Sync
NOTE: The signature of calling this has changed - the bucket is now read from the properties file in lieu of entering it into the command line

This is a compiled Jar and config file for uploading to Corral S3 MinIO Server/Bucket.
It uses the following syntax<br/>
`java -jar out/artifacts/chai_s3_datasync/chai-s3-datasync.jar /Users/porterba/Downloads/ThematicMap_GIS/
`<br/>
WHERE the first parameter `/Users/porterba/Downloads/ThematicMap_GIS/` is the folder you want the jar to recursively parse.

_The **OLD** syntax was the following<br/>
`java -jar out/artifacts/chai_s3_datasync/chai-s3-datasync.jar upstream /Users/porterba/Downloads/ThematicMap_GIS/
`<br/>
WHERE the first parameter `upstream` is the bucket to upload to <br/>& the second parameter `/Users/porterba/Downloads/ThematicMap_GIS/` is the folder you want the jar to recursively parse._
