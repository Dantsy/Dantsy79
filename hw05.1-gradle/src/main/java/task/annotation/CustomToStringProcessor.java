package task.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("task.model.CustomToString")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CustomToStringProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : annotatedElements) {
                if (element.getKind() == ElementKind.CLASS) {
                    TypeElement classElement = (TypeElement) element;
                    generateToStringMethod(classElement);
                } else {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "@CustomToString can only be used on classes", element);
                }
            }
        }
        return true;
    }

    private void generateToStringMethod(TypeElement classElement) {
        String className = classElement.getQualifiedName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).toString();
        StringBuilder methodBody = new StringBuilder();
        methodBody.append("return \"").append(className).append("{");

        for (Element enclosedElement : classElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                VariableElement field = (VariableElement) enclosedElement;
                methodBody.append(" %s=").append(field.getSimpleName()).append("=%s,");
            }
        }

        methodBody.append("}\";");

        StringBuilder toStringMethod = new StringBuilder();
        toStringMethod.append("@Override\n")
                .append("public String toString() {\n")
                .append("return String.format(\"").append(methodBody).append("\", ");

        for (Element enclosedElement : classElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                VariableElement field = (VariableElement) enclosedElement;
                toStringMethod.append(field.getSimpleName()).append(", ");
            }
        }

        toStringMethod.append(");\n")
                .append("}");

        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(className + "CustomToString");
            Writer writer = sourceFile.openWriter();
            writer.append("package ").append(packageName).append(";\n\n");
            writer.append("public class ").append(classElement.getSimpleName()).append(" {\n");
            writer.append(toStringMethod).append("\n");
            writer.append("}\n");
            writer.close();
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Error generating toString method for " + className, classElement);
        }
    }
}