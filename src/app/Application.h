/*
* <one line to give the library's name and an idea of what it does.>
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

#ifndef TESTER_APPLICATION_H
#define TESTER_APPLICATION_H

#include "../common/logger.h"
#include "../api/Kernel.h"

#include<string>

namespace Tester {
	class Application {
	public:
		Application(std::string in, std::string out, common::Logger * log) :
			_log(log), 
			_in(in), 
			_out(out) {
			_log->debug("Starting Universal API tester!");
		};
		
		uint8_t run();

	private:
		common::Logger * _log;
		std::string _in;
		std::string _out;
	};

}

#endif // TESTER_APPLICATION_H
