/*
 * This is a simple plugin for demo purpose.
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

// Export symbols form the Plugin-API
#define UAPI_TESTER_PLUGIN_SYSTEM_DEMO_SOURCE 1

#include "demo_config.h"

#include <string>
#include <iostream>
#include <stdexcept>

namespace Tester {
	namespace api {
		namespace systems {
			class DemoSystem : public SystemPlugin::System {
			public:
				UAPI_TESTER_API virtual ~DemoSystem() {}
				
				UAPI_TESTER_API virtual const std::string &getDescription() const {
					static std::string name("DEMO Systems-Plugin library");
					return name;
				}
				
				UAPI_TESTER_API virtual uint8_t start() {
					_log->debug(std::string("Started: ") + getName());
					return 0;
				};
			};

			// Register the Plugin.
			extern "C" UAPI_TESTER_API int getEngineVersion() {
				return 1;
			}
			extern "C" UAPI_TESTER_API void registerPlugin(Kernel &kernel, std::string &name) {
				kernel.addSystemPlugin( new DemoSystem(), name );
			}
		}
	}
}
