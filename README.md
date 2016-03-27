# Growing Object Oriented Software Guided By: Test Driven Development

## 第10章 可行走的骨架
- 可行走骨架能够帮助我们进一步了解需求；
- 团队在第“0”次迭代中进行初步分析，并建立起物理和技术环境；
- 从测试退回到开发工作，有助于将注意力集中在系统需要完成的任务上；
- 每次只处理一个领域使开发者关注当前领域的相关知识，无需了解支持当前领域的底层细节，当底层细节需要修改时，当前领域无需进行修改，只需编写一些针对当前领域的实现，然后将过期的领域实现替换，整个领域层位于底层实现的抽象层（[依赖倒转原则](https://www.baidu.com/link?url=A2HNv2USki03_3fnPlECmNApiX956T3AdAYWDfX9cBFY0tNJevbzInsbU3nf6tw-XNSmv36hfxN4qC6WJ2KhAR5zWxswI5HNt-fFPiPsCuhzY1XkbYvun47MGGoejcemI5J2Hv5hQ0zYVSRtI-vh75IvQMWQ2nwy0MwxH44Oqj1scTP9n08js-6SM9yAUk16K4_5ttany6bPkgXJ9pvynK&wd=&eqid=d6e077220009d7ed0000000556f3ff04)的具体表现）；