/*
 * Fabric to load and work with different kind of Plugins:
 * - Systems:  Pool of Systems to start up before a Request is sent out.
 *             Normally this may be only one System but in case of a simulation
 *             of a couple of Sidecars for example, these can be used for it.
 * - Requests: Pool of Plugins to create a Request to one of the Systems.
 *             These are the main entry points (input data) for a test.
 * - Listener: Starting a listener and return any kind of defined value.
 * 
 * Usage:
 * api::Kernel kernel(this->_log);
 * kernel.load("systems_demo");
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
#ifndef UAPI_TESTER_PLUGIN_KERNEL_H
#define UAPI_TESTER_PLUGIN_KERNEL_H

#include "../common/logger.h"

#include "PluginConfig.h"

#include "SystemPlugin.h"
#include "RequestsPlugin.h"
#include "ListenerPlugin.h"

#include "../common/Plugin.h"

#include <string>
#include <map>
#include <stdexcept>

namespace Tester {
	namespace api {
		class Kernel {
		public:
			UAPI_TESTER_API Kernel(common::Logger * log) :
			_log(log),
			systemPlugins(systems::SystemPlugin(_log)),
			listenerPlugins(listener::ListenerPlugin(_log)),
			requestPlugins(requests::RequestsPlugin(_log)) { }

			UAPI_TESTER_API void load(const std::string &filename) {
				if (this->loaded.find(filename) == this->loaded.end()) {
					this->loaded.insert(
						Plugins::value_type(filename, common::Plugin(filename, _log))
					).first->second.registerPlugin(*this);
				}
			}

			UAPI_TESTER_API void addSystemPlugin(systems::SystemPlugin::System * plugin, std::string &name) {
				plugin->setLog(_log);
				plugin->setName(name);
				this->systemPlugins.add(plugin);
			}

			systems::SystemPlugin::System * getSystem(const std::string &name) {
				return this->systemPlugins.get(name);
			}

			UAPI_TESTER_API void addListenerPlugin(listener::ListenerPlugin::Listener * plugin, std::string &name) {
				plugin->setLog(_log);
				plugin->setName(name);
				this->listenerPlugins.add(plugin);
			}

			listener::ListenerPlugin::Listener * getListener(const std::string &name) {
				return this->listenerPlugins.get(name);
			}

			UAPI_TESTER_API void addRequestPlugin(requests::RequestsPlugin::Request * plugin, std::string &name) {
				plugin->setLog(_log);
				plugin->setName(name);
				this->requestPlugins.add(plugin);
			}

			requests::RequestsPlugin::Request * getRequest(const std::string &name) {
				return this->requestPlugins.get(name);
			}

		private:
			typedef std::map<std::string, common::Plugin> Plugins;
			common::Logger * _log;
			Plugins loaded;
			systems::SystemPlugin systemPlugins;
			listener::ListenerPlugin listenerPlugins;
			requests::RequestsPlugin requestPlugins;
		};
	}
}
#endif // UAPI_TESTER_PLUGIN_KERNEL_H
