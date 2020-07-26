/*
 * Wrapper-Class (Fabric) around the real Plugins to load them and holding the Handler.
 * This is only a internal class to manage the loading and caching process.
 * 
 * The Kernel can create a new Instance of this Plugin, this then will use the SharedLibraryLoader
 * to load the shared library. After it's loaded, this lugin calls the register functions
 * from the loaded shared object which then should call the Kernel to register itself.
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

#ifndef UAPI_TESTER_PLUGIN_H
#define UAPI_TESTER_PLUGIN_H

#include "../api/PluginConfig.h"
#include "logger.h"
#include "SharedLibraryLoader.h"

namespace Tester { namespace api { class Kernel; } };
namespace common {
	class Plugin {
	public:
		UAPI_TESTER_API Plugin(const std::string &filename, Logger * log);
		UAPI_TESTER_API Plugin(const Plugin &other);
		UAPI_TESTER_API virtual ~Plugin();

		UAPI_TESTER_API int getEngineVersion() const {
			return this->getEngineVersionAddress();
		}

		UAPI_TESTER_API void registerPlugin(Tester::api::Kernel &kernel) {
			this->registerPluginAddress(kernel, _name);
		}

		Plugin &operator= (const Plugin &other);

	private:
		typedef int GetEngineVersionFunction();
		typedef void RegisterPluginFunction(Tester::api::Kernel &, std::string &);
		
		std::string _name;
		Logger * _log;
		SharedLibraryLoader::HandleType _handle;
		size_t * _referenceCount;
		GetEngineVersionFunction * getEngineVersionAddress;
		RegisterPluginFunction * registerPluginAddress;
	};
}
#endif // UAPI_TESTER_PLUGIN_H
