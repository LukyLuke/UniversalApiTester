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

// Export the symbols for the Plugin-API
#define UAPI_TESTER_SOURCE 1

#include "Plugin.h"
#include <stdexcept>

namespace common {

	Plugin::Plugin(const std::string &filename, Logger * log) :
	_name(filename),
	_log(log),
	_handle(0),
	_referenceCount(0),
	getEngineVersionAddress(0),
	registerPluginAddress(0) {
		_log->debug(std::string("Loading library: ") + _name);
		try {
			this->_handle = SharedLibraryLoader::loadLibrary(_name);
			this->getEngineVersionAddress = SharedLibraryLoader::getFunctionPointer<GetEngineVersionFunction>(this->_handle, "getEngineVersion");
			this->registerPluginAddress = SharedLibraryLoader::getFunctionPointer<RegisterPluginFunction>(this->_handle, "registerPlugin");
			this->_referenceCount = new size_t(1);
		}
		catch(const std::exception &e) {
			SharedLibraryLoader::unloadLibrary(this->_handle);
			_log->error(std::string("Failed to load: ") + _name);
			_log->debug(e.what());
			throw;
		}
	}

	Plugin::Plugin(const Plugin &other) : 
	_name(other._name),
	_log(other._log),
	_handle(other._handle),
	_referenceCount(other._referenceCount),
	getEngineVersionAddress(other.getEngineVersionAddress),
	registerPluginAddress(other.registerPluginAddress) {
		if (this->_referenceCount) {
			++(*this->_referenceCount);
		}
	}

	Plugin::~Plugin() {
		int remaining = --(*this->_referenceCount);
		if (remaining == 0) {
			delete this->_referenceCount;
			SharedLibraryLoader::unloadLibrary(this->_handle);
		}
	}

}
