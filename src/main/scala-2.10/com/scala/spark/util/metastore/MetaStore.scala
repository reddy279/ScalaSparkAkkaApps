package com.scala.spark.util.metastore



case class file_format(
                        fieldName: Option[String],
                        filedType: Option[String]
                        )

case class nodes(file_format: List[file_format],
                 file_location: String,
                 db_tbl_name:String)

case class MetaStore(metastore: Map[String, nodes])