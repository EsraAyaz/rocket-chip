// See LICENSE.SiFive for license details.

package freechips.rocketchip.util

import Chisel._

/** Reset: asynchronous assert,
  *  synchronous de-assert
  *
  */

class ResetCatchAndSync (sync: Int = 3) extends Module {

  val io = new Bundle {
    val sync_reset = Bool(OUTPUT)
  }

  io.sync_reset := ~AsyncResetSynchronizerShiftReg(Bool(true), sync)

}

object ResetCatchAndSync {

  def apply(clk: Clock, rst: Bool, sync: Int = 3, name: Option[String] = None): Bool = {

    val catcher = Module (new ResetCatchAndSync(sync))
    if (name.isDefined) {catcher.suggestName(name.get)}
    catcher.clock := clk
    catcher.reset := rst

    catcher.io.sync_reset
  }

  def apply(clk: Clock, rst: Bool, sync: Int, name: String): Bool = apply(clk, rst, sync, Some(name))
  def apply(clk: Clock, rst: Bool, name: String): Bool = apply(clk, rst, name = Some(name))

}
