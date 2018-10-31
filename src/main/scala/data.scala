package ohnosequences.db.rna16s

import ohnosequences.db.rnacentral
import ohnosequences.s3._
import ohnosequences.files.digest.DigestFunction
import ohnosequences.files.File

sealed abstract class Version(val name: String) {
  val compatibleInputVersions: Set[RNACentralVersion]
  val inputVersion: RNACentralVersion

  override final def toString: String = name
}

object Version {

  lazy val all: Set[Version] =
    Set(v10_0)

  case object v10_0 extends Version("10.0") {
    val inputVersion            = rnacentral.Version._10_0
    val compatibleInputVersions = Set(inputVersion)
  }
}

case object data {

  case object local {

    def idMappingFile(version: Version, localFolder: File): File =
      new File(localFolder, "id_mapping.tsv")

    def fastaFile(version: Version, localFolder: File): File =
      new File(localFolder, "rnacentral_species_specific_ids.fasta")
  }

  val s3Prefix: Version => S3Folder =
    version =>
      s3"resources.ohnosequences.com" /
        "ohnosequences" /
        "db" /
        "rna16s" /
        "unstable" /
        version.toString /

  val sequences: Version => S3Object =
    s3Prefix(_) / "rna16s.fa"

  val hashingFunction: DigestFunction = DigestFunction.SHA512
}
