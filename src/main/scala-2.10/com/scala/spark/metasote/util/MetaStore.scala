package com.scala.spark.metasote.util



case class file_format(
                        fieldName: String,
                        filedType: String
                        )

case class nodes(file_format: List[file_format],
                 file_location: String,
                 db_tbl_name: String)

case class metastore(metastore: Map[String, nodes])