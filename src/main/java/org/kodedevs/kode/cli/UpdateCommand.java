/*
 * Copyright 2022 Kode Devs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kodedevs.kode.cli;

import org.kodedevs.kode.tools.updater.AppUpdater;
import org.kodedevs.kode.utils.ReleaseInfo;
import picocli.CommandLine.Command;

import java.util.Scanner;

@Command(name = "update")
public class UpdateCommand implements Runnable {

    @Override
    public void run() {
        // Print current version
        System.out.printf("Kode version %s%n", ReleaseInfo.getVersion());

        // Check for update
        String newVersion = AppUpdater.checkForAvailableUpdate();
        if (newVersion == null) {
            // No updates found (OK).
            System.out.println("Up to date");
        } else {
            // Update available
            System.out.println("Update " + newVersion + " is available");

            System.out.printf("Download and install Kode version %s [N/y]? ", newVersion);
            if (new Scanner(System.in).nextLine().equalsIgnoreCase("y")) {
                AppUpdater.install();
            }
        }
    }
}
