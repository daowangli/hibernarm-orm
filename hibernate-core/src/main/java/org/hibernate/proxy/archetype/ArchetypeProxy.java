/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 * daowangli@gmail.com
 */
package org.hibernate.proxy.archetype;
import java.io.Serializable;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.openehr.rm.common.archetyped.Locatable;

/**
 * Proxy for "dynamic-map" entity representations.
 *
 * @author Gavin King
 */
public class ArchetypeProxy implements HibernateProxy, Serializable {

	private ArchetypeLazyInitializer li;

	ArchetypeProxy(ArchetypeLazyInitializer li) {
		this.li = li;
	}

	public Object writeReplace() {
		return li.getImplementation();
	}

	public LazyInitializer getHibernateLazyInitializer() {
		return li;
	}

	public Locatable readOnlyTarget() {
		return (Locatable) li.getImplementation();
	}

}
