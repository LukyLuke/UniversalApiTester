/*
 * Utility-Class to load/Unload shared libraries.
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
#ifndef UAPI_TESTER_SHARED_LIBRARY_LOADER_H
#define UAPI_TESTER_SHARED_LIBRARY_LOADER_H

#include "../api/PluginConfig.h"
#include <dlfcn.h>

namespace common {
	class SharedLibraryLoader {
	public:
		typedef void * HandleType;
		
		UAPI_TESTER_API static HandleType loadLibrary(const std::string &path) {
			std::string fullPath = std::string("./../lib/lib") + path + ".so";
			void *sharedObject = ::dlopen(fullPath.c_str(), RTLD_NOW);
			if (sharedObject == NULL) {
				throw std::runtime_error(std::string("Unable to load shared library '") + fullPath + "'");
			}
			return sharedObject;
		}

		UAPI_TESTER_API static void unloadLibrary(HandleType handle) {
			if (handle == NULL) {
				return;
			}
			int result = ::dlclose(handle);
			if (result != 0) {
				throw std::runtime_error("Unable to free a shared library");
			}
		}

		template<typename TSignature> static TSignature *getFunctionPointer(HandleType handle, const std::string &functionName) {
			::dlerror(); // clear the error before we call the function
			void *functionAddress = ::dlsym(handle, functionName.c_str());

			const char *error = ::dlerror();
			if (error != NULL) {
				throw std::runtime_error(std::string("Could not find exported function '") + functionName + "'");
			}
			return reinterpret_cast<TSignature *>(functionAddress);
		}
	};
}

#endif // UAPI_TESTER_SHARED_LIBRARY_LOADER_H

