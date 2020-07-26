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

#include "logger.h"

using namespace common;

void Logger::setLevel(uint8_t level) {
	this->level = level;
	this->debug("Verbose logging: on");
}

void Logger::setFormat(std::string _format) {
	this->format = _format;
}

void Logger::debug(std::string value) {
	if (level >= this->VERBOSE) {
		_log() << "[DEBUG] " << value << std::endl;
	}
}

void Logger::info(std::string value) {
	_log() << value << std::endl;
}

void Logger::error(std::string value) {
	_log(std::cerr) << "[ERROR] " << value << std::endl;
}

std::ostream& Logger::_log(std::ostream& stream) {
	std::time_t _time = std::time(nullptr);
	std::tm _tm = *std::localtime(&_time);
	return stream << std::put_time(&_tm, this->format.c_str()) << "\t";
}
