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

#ifndef COMMON_LOGGER_H
#define COMMON_LOGGER_H

#include<ctime>
#include<iomanip>
#include<ostream>
#include<iostream>
#include<cstdarg>
#include<stdint.h>

namespace common {

class Logger
{
public:
	static const uint8_t VERBOSE = 1;

	Logger(uint8_t _level = 0, std::string _format =  "%c %z") : level(_level), format(_format) {};

	void setLevel(uint8_t level);
	void setFormat(std::string _format);

	void info(std::string value);
	void debug(std::string value);
	void error(std::string value);
  
private:
	uint8_t level;
	std::string format;

	std::ostream& _log(std::ostream& stream = std::cout);
};

}

#endif // COMMON_LOGGER_H
