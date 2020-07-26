/*
 * Base Class/Interface for all plugins which implements a System.
 * 
 * These are system which are started up physically.
 * Normally this is the main Application we want to test, but also can
 * be other side-systems which we just have to have and not want to mock.
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

#ifndef UAPI_TESTER_SYSTEMS_PLUGIN_H
#define UAPI_TESTER_SYSTEMS_PLUGIN_H

#include "../common/logger.h"
#include "PluginConfig.h"

#include <vector>
#include <string>

namespace Tester {
	namespace api {
		namespace systems {
			class SystemPlugin {
			public:
				/**
				 * This is the real plugin interface for the implementations.
				 */
				class System {
				public:
					System() {};
					virtual ~System() {};
					virtual const std::string &getDescription() const = 0;
					virtual uint8_t start() = 0;
					
					void setName(std::string &name) { _name = name; }
					const std::string &getName() const { return _name; };
					
					void setLog(common::Logger * log) { _log = log; }
					void setConfig(std::string config) { _config = config; }
					
				protected:
					std::string _name;
					std::string _config;
					common::Logger * _log;
				};
				
				
				UAPI_TESTER_API SystemPlugin(common::Logger * log) :
				_log(log) {};
				
				UAPI_TESTER_API ~SystemPlugin() {
					for (Systems::const_iterator it = this->plugins.begin(); it != this->plugins.end(); ++it) {
						delete *it;
					}
				};
				
				UAPI_TESTER_API void add(SystemPlugin::System * plugin) {
					this->plugins.push_back(plugin);
				}
				
				UAPI_TESTER_API System * get(const std::string &name) {
					for (System * system : this->plugins) {
						if (system->getName().compare(name) == 0) {
							return system;
						}
					}
					return nullptr;
				}
				
			private:
				typedef std::vector<System *> Systems;
				common::Logger * _log;
				Systems plugins;
			};
		}
	}
}

#endif // UAPI_TESTER_SYSTEMS_PLUGIN_H
