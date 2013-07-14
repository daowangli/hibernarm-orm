/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2013, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
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
 */
package org.hibernate.persister.walking.internal;

import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.walking.spi.AttributeDefinition;
import org.hibernate.persister.walking.spi.AttributeSource;
import org.hibernate.persister.walking.spi.CompositionDefinition;
import org.hibernate.persister.walking.spi.EncapsulatedEntityIdentifierDefinition;
import org.hibernate.persister.walking.spi.EntityDefinition;
import org.hibernate.persister.walking.spi.EntityIdentifierDefinition;
import org.hibernate.persister.walking.spi.NonEncapsulatedEntityIdentifierDefinition;
import org.hibernate.type.Type;

/**
 * @author Gail Badner
 */
public class EntityIdentifierDefinitionHelper {

	public static EntityIdentifierDefinition buildSimpleEncapsulatedIdentifierDefinition(final AbstractEntityPersister entityPersister) {
		return new EncapsulatedEntityIdentifierDefinition() {
			@Override
			public AttributeDefinition getAttributeDefinition() {
				return new AttributeDefinitionAdapter( entityPersister);
			}

			@Override
			public boolean isEncapsulated() {
				return true;
			}

			@Override
			public EntityDefinition getEntityDefinition() {
				return entityPersister;
			}
		};
	}

	public static EntityIdentifierDefinition buildEncapsulatedCompositeIdentifierDefinition(
			final AbstractEntityPersister entityPersister) {

		return new EncapsulatedEntityIdentifierDefinition() {
			@Override
			public AttributeDefinition getAttributeDefinition() {
				return new CompositionDefinitionAdapter( entityPersister );
			}

			@Override
			public boolean isEncapsulated() {
				return true;
			}

			@Override
			public EntityDefinition getEntityDefinition() {
				return entityPersister;
			}
		};
	}

	public static EntityIdentifierDefinition buildNonEncapsulatedCompositeIdentifierDefinition(final AbstractEntityPersister entityPersister) {
		return new NonEncapsulatedEntityIdentifierDefinition() {
			@Override
			public Iterable<AttributeDefinition> getAttributes() {
				return CompositionSingularSubAttributesHelper.getIdentifierSubAttributes( entityPersister );
			}

			@Override
			public Class getSeparateIdentifierMappingClass() {
				return entityPersister.getEntityMetamodel().getIdentifierProperty().getType().getReturnedClass();
			}

			@Override
			public boolean isEncapsulated() {
				return false;
			}

			@Override
			public EntityDefinition getEntityDefinition() {
				return entityPersister;
			}
		};
	}

	private static class AttributeDefinitionAdapter implements AttributeDefinition {
		private final AbstractEntityPersister entityPersister;

		AttributeDefinitionAdapter(AbstractEntityPersister entityPersister) {
			this.entityPersister = entityPersister;
		}

		@Override
		public String getName() {
			return entityPersister.getEntityMetamodel().getIdentifierProperty().getName();
		}

		@Override
		public Type getType() {
			return entityPersister.getEntityMetamodel().getIdentifierProperty().getType();
		}

		@Override
		public AttributeSource getSource() {
			return entityPersister;
		}

		@Override
		public String toString() {
			return "<identifier-property:" + getName() + ">";
		}

		protected AbstractEntityPersister getEntityPersister() {
			return entityPersister;
		}
	}

	private static class CompositionDefinitionAdapter extends AttributeDefinitionAdapter implements CompositionDefinition {

		CompositionDefinitionAdapter(AbstractEntityPersister entityPersister) {
			super( entityPersister );
		}

		@Override
		public String toString() {
			return "<identifier-property:" + getName() + ">";
		}

		@Override
		public Iterable<AttributeDefinition> getAttributes() {
			return  CompositionSingularSubAttributesHelper.getIdentifierSubAttributes( getEntityPersister() );
		}
	}
}
