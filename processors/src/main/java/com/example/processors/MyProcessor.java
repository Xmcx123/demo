package com.example.processors;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import com.example.annotations.MyAnnotation;
import com.google.auto.service.AutoService;

/**方案1
 * 运行注解处理器
 * 1、在processors库的main目录下新建resources资源文件夹；
 * 2、在resources文件夹下建立META_INF/services 目录文件夹；
 * 3、在META-INF/services目录文件夹下创建 javax.annotation.processing.Processor 文件；
 *  * 4、在 javax.annotation.processing.Processor 文件写入注解处理器的全称，包括包路径；
 */

/** 方案2
 * 每一个注解处理器类都必须有一个空的构造函数，默认不写就行;
 */
@AutoService(Processor.class)//注册
public class MyProcessor extends AbstractProcessor {

    /**
     * 这相当于每个处理器的主函数main()，你在这里写你的扫描、评估和处理注解的代码，以及生成Java文件。
     * 输入参数RoundEnviroment，可以让你查询出包含特定注解的被注解元素
     * @param annotations   请求处理的注解类型
     * @param roundEnvironment  有关当前和以前的信息环境
     * @return  如果返回 true，则这些注解已声明并且不要求后续 Processor 处理它们；
     *          如果返回 false，则这些注解未声明并且可能要求后续 Processor 处理它们
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        // roundEnv.getElementsAnnotatedWith()返回使用给定注解类型的元素
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(MyAnnotation.class);
        for (Element element:
             elementsAnnotatedWith) {
            // 判断元素的类型为Class
            if (element.getKind() == ElementKind.CLASS) {
                // 显示转换元素类型
                TypeElement typeElement = (TypeElement) element;
                // 输出元素名称
                System.out.println("Zero: " + typeElement.getSimpleName() + " : " + System.currentTimeMillis());
                // 输出注解属性值
                System.out.println("Zero: " +typeElement.getAnnotation(MyAnnotation.class).value());
            }
            System.out.println("------------------------------");
        }
        return true;
    }
}
