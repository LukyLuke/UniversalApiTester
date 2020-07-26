/*
 * The main Application:
 * - Reads the configuration
 * - Loads all the needed plugins
 * - Starts all the listeners
 * - Starts all the systems
 * - Runs all the requests
 * - Cummulates the responses and returns them
 * 
 * Copyright (C) 2020  Lukas zurschmiede <lukas.zurschmiede@ranta.ch>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#include "Application.h"

namespace Tester {

	uint8_t Application::run() {
		_log->info(std::string("Configuration: ").append(this->_in));
		_log->info(std::string("Output: ").append(this->_out));
		
		api::Kernel kernel(this->_log);
		
		// Load and start the system_demo plugin.
		kernel.load("systems_demo");
		api::systems::SystemPlugin::System * system = kernel.getSystem("systems_demo");
		if (system != nullptr) {
			system->start();
		}
		
		return EXIT_SUCCESS;
	}

}
