package com.goff.packages.domain;

import com.goff.packages.domain.invalid.InvalidClass; // Noncompliant {{Não é permitido utilizar sub-pacotes do pacote "com.goff.packages.domain".}}

public class AccessingPackageBelow {

    private InvalidClass invalid;
}
