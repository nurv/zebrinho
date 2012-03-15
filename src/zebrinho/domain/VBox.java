/* VBox to handle laziness through indirection
   Copyright (C) 2012  Artur Ventura

Author: Artur Ventura

This file is part of Zebrinho.

Zebrinho is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Zebrinho is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Emacs.  If not, see <http://www.gnu.org/licenses/>.  */
package zebrinho.domain;

public abstract class VBox<T extends DBEntity> {
	private boolean loaded;
	private T object;
	private int id;
	public T getObject() {
		if (!loaded){
			setObject(load());
			loaded = true;
		}
		return object;
		
	}

	public abstract T load();

	public void setObject(T object) {
		this.object = object;
		this.id = object.getId();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
}
