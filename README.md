# Plugin Knime para conexión con Moodle

TFM. Máster Universitario Online en Inteligencia de Negocio y Big Data en Entornos Seguros 
Interuniversitario (Universidades de Burgos, León y Valladolid)

**Realizado por**: Francisco Gil Rodríguez

**Tutores**: Dr. José Francisco Díez Pastor y Dr. César Ignacio García Osorio

---

### Resumen

KNIME es una plataforma de código abierto orientada al análisis de datos que permite, a través de una interfaz intuitiva y visual, crear análisis complejos y flujos de trabajo mediante componentes especializados en diferentes áreas como la minería de datos, el aprendizaje automático o la visualización de datos. 

Moodle es una plataforma de aprendizaje (Learning Management System) ampliamente utilizada por instituciones educativas de todo el mundo. 

En este trabajo se han desarrollado nuevos componentes de KNIME que permiten la integración en flujos de trabajo de KNIME de los datos que Moodle va registrando durante la ejecución de una acción formativa. Con un enfoque orientado al rol de profesor, se pretende que cualquier usuario con este rol dentro de una plataforma Moodle, pueda realizar estudios externos sobre los datos de las acciones formativas a las que tiene acceso. 

Adicionalmente se ha implementado un flujo de trabajo en KNIME sobre datos reales extraidos de Moodle, analizando diferentes técnicas de aprendizaje automático supervisado.

### Abstract

KNIME is an open source platform oriented to data analysis that allows, through an intuitive and visual interface, the creation of complex analysis and workflows through specialized components in different areas such as data mining, machine learning or data visualization. 

Moodle is a learning platform (Learning Management System) widely used by educational institutions around the world. 

In this work we have developed new Knime components that allow the integration of the data recorded by Moodle during the execution of  a training action into KNIME workflows. With an approach oriented to the role of teacher, it is intended that any user with this role within a Moodle platform, can perform external studies on the data of the training actions to which they have access. 

In addition, a workflow have been implemented in KNIME on real data extracted from Moodle, analyzing different supervised machine learning techniques.

### Extensión Knime Moodle Integration

La extensión Knime Moodle Integration desarrollada en este proyecto incluye los siguientes nodos: 


![](https://github.com/frankgil/knime-moodle/blob/main/doc/img/nodes_moodle_todos.png?raw=true)
*Nodos implementados en la extensión KNIME Moodle Integration.*

 - **Moodle Connector**. Establece la conexión con una plataforma Moodle. Requiere una cuenta con perfil de profesor. Requiere que la plataforma Moodle tenga activado el acceso a la aplicación móvil. El nodo devuelve a través de un puerto de salida la información de sesión, de forma que, conectada a otros nodos de la extensión, estos pueden extraer información de Moodle sin necesidad de volver a conectarse.
 - **Moodle Courses**. Extrae información de cursos. Permite obtener uno o varios cursos según su identificador o todos los cursos dentro de las categorías especificadas.
 - **Moodle Users**. Extrae información de usuarios según los cursos especificados. También es posible devolver solo los usuarios con rol estudiante, estimar el género de cada usuario a partir de su nombre y anonimizar los datos personales.
 - **Moodle Reports Logs**. Extrae información de logs de la plataforma Moodle. Este nodo está diseñado para poder extraer logs de forma homogénea aunque provengan de diferentes fuentes dentro de Moodle (Logs, Live logs, Activity Reports, Overview Statistics, Course Participation, Activity completion, Statistics, etc.).
 - **Moodle Reports Grades**. Extrae información de calificaciones agrupadas por curso, estudiante y actividad. Adicionalmente, devuelve una columna con la calificación final obtenida.
 - **Moodle Reports Quizzes**. Extrae información sobre los intentos realizados por los estudiantes en los cuestionarios.


