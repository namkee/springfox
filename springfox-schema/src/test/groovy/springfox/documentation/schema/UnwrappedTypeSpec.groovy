/*
 *
 *  Copyright 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.documentation.schema
import spock.lang.Specification
import spock.lang.Unroll
import springfox.documentation.schema.mixins.ConfiguredObjectMapperSupport
import springfox.documentation.schema.mixins.ModelProviderSupport
import springfox.documentation.schema.mixins.TypesForTestingSupport

import static springfox.documentation.spi.DocumentationType.*
import static springfox.documentation.spi.schema.contexts.ModelContext.*

@Mixin([TypesForTestingSupport, ModelProviderSupport, ConfiguredObjectMapperSupport, AlternateTypesSupport])
class UnwrappedTypeSpec extends Specification {
  @Unroll
  def "Unwrapped types are rendered correctly for fields"() {
    given:
      def provider = defaultModelProvider(objectMapperThatUsesFields())
      def namingStrategy = new DefaultGenericTypeNamingStrategy()
    when:
      Model asInput = provider.modelFor(
          inputParam(
              UnwrappedTypeForField,
              SWAGGER_12,
              alternateTypeProvider(),
              namingStrategy))
          .get()
      Model asReturn = provider.modelFor(
          returnValue(
              UnwrappedTypeForField,
              SWAGGER_12,
              alternateTypeProvider(),
              namingStrategy))
          .get()

    then:
      asInput.getName() == UnwrappedTypeForField.simpleName
      asInput.getProperties().size() == 1
      asInput.getProperties().containsKey("name" )
      def modelProperty = asInput.getProperties().get("name" )
      modelProperty.type.erasedType == String
      modelProperty.getQualifiedType() == "java.lang.String"
      def item = modelProperty.getModelRef()
      item.type == "string"
      !item.collection
      item.itemType == null
    and:
      asReturn.getName() == UnwrappedTypeForField.simpleName
      asReturn.getProperties().size() == 1
      asReturn.getProperties().containsKey("name" )
      def returnProperty = asReturn.getProperties().get("name" )
      returnProperty.type.erasedType == String
      returnProperty.getQualifiedType() == "java.lang.String"
      def returnItem = modelProperty.getModelRef()
      returnItem.type == "string"
      !returnItem.collection
      returnItem.itemType == null

  }

  @Unroll
  def "Unwrapped types are rendered correctly for getters"() {
    given:
      def provider = defaultModelProvider(objectMapperThatUsesGetters())
      def namingStrategy = new DefaultGenericTypeNamingStrategy()
    when:
      Model asInput = provider.modelFor(inputParam(UnwrappedTypeForGetter, SWAGGER_12, alternateTypeProvider(),
          namingStrategy)).get()
      Model asReturn = provider.modelFor(returnValue(UnwrappedTypeForGetter, SWAGGER_12, alternateTypeProvider(),
          namingStrategy)).get()

    then:
      asInput.getName() == UnwrappedTypeForGetter.simpleName
      asInput.getProperties().size() == 1
      asInput.getProperties().containsKey("category")
      def modelProperty = asInput.getProperties().get("category")
      modelProperty.type.erasedType == Category
      modelProperty.getQualifiedType() == "springfox.documentation.schema.Category"
      def item = modelProperty.getModelRef()
      item.type == "Category"
      !item.collection
      item.itemType == null

      asReturn.getName() == UnwrappedTypeForGetter.simpleName
      asReturn.getProperties().size() == 1
      asReturn.getProperties().containsKey("name")
      def retModelProperty = asReturn.getProperties().get("name")
      retModelProperty.type.erasedType == String
      retModelProperty.getQualifiedType() == "java.lang.String"
      def retItem = retModelProperty.getModelRef()
      retItem.type == "string"
      !retItem.collection
      retItem.itemType == null

  }

  @Unroll
  def "Unwrapped types are rendered correctly for setters"() {
    given:
      def provider = defaultModelProvider(objectMapperThatUsesSetters() )
      def namingStrategy = new DefaultGenericTypeNamingStrategy()
    when:
      Model asInput = provider.modelFor(inputParam(UnwrappedTypeForSetter, SWAGGER_12, alternateTypeProvider(),
          namingStrategy)).get()
      Model asReturn = provider.modelFor(returnValue(UnwrappedTypeForSetter, SWAGGER_12, alternateTypeProvider(),
        namingStrategy)).get()

    then:
      asInput.getName() == UnwrappedTypeForSetter.simpleName
      asInput.getProperties().size() == 1
      asInput.getProperties().containsKey("name")
      def modelProperty = asInput.getProperties().get("name")
      modelProperty.type.erasedType == String
      modelProperty.getQualifiedType() == "java.lang.String"
      def item = modelProperty.getModelRef()
      item.type == "string"
      !item.collection
      item.itemType == null

      asReturn.getName() == UnwrappedTypeForSetter.simpleName
      asReturn.getProperties().size() == 1
      asReturn.getProperties().containsKey("category")
      def retModelProperty = asReturn.getProperties().get("category")
      retModelProperty.type.erasedType == Category
      retModelProperty.getQualifiedType() == "springfox.documentation.schema.Category"
      def retItem = retModelProperty.getModelRef()
      retItem.type == "Category"
      !retItem.collection
      retItem.itemType == null

  }

}
