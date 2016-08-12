package ohnosequences.db.rna16s.test

import ohnosequences.statika._, aws._
import ohnosequences.awstools._, regions.Region._, ec2._, InstanceType._, autoscaling._, s3._

case object compats {

  class DefaultCompatible[B <: AnyBundle](bundle: B, javaHeap: Int) extends Compatible(
    amznAMIEnv(AmazonLinuxAMI(Ireland, HVM, InstanceStore), javaHeap),
    bundle,
    ohnosequences.generated.metadata.db_rna16s
  )

  case object pick16SCandidates extends DefaultCompatible(ohnosequences.db.rna16s.test.pick16SCandidates, javaHeap = 40)

  case object dropRedundantAssignmentsAndGenerate extends DefaultCompatible(ohnosequences.db.rna16s.test.dropRedundantAssignmentsAndGenerate, javaHeap = 10)
  case object dropInconsistentAssignmentsAndGenerate extends DefaultCompatible(ohnosequences.db.rna16s.test.dropInconsistentAssignmentsAndGenerate, javaHeap = 10)
}
