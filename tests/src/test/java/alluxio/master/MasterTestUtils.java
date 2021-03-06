/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.master;

import alluxio.Configuration;
import alluxio.PropertyKey;
import alluxio.master.block.BlockMaster;
import alluxio.master.file.FileSystemMaster;
import alluxio.master.journal.JournalFactory;

import java.io.IOException;

public class MasterTestUtils {
  public static FileSystemMaster createLeaderFileSystemMasterFromJournal()
      throws IOException {
    String masterJournal = Configuration.get(PropertyKey.MASTER_JOURNAL_FOLDER);
    JournalFactory journalFactory = new JournalFactory.ReadWrite(masterJournal);
    BlockMaster blockMaster = new BlockMaster(journalFactory);
    FileSystemMaster fsMaster = new FileSystemMaster(blockMaster, journalFactory);
    blockMaster.start(true);
    fsMaster.start(true);
    return fsMaster;
  }

  public static FileSystemMaster createStandbyFileSystemMasterFromJournal()
      throws IOException {
    String masterJournal = Configuration.get(PropertyKey.MASTER_JOURNAL_FOLDER);
    JournalFactory journalFactory = new JournalFactory.ReadWrite(masterJournal);
    BlockMaster blockMaster = new BlockMaster(journalFactory);
    FileSystemMaster fsMaster = new FileSystemMaster(blockMaster, journalFactory);
    blockMaster.start(false);
    fsMaster.start(false);
    return fsMaster;
  }
}
