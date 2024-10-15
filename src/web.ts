import { WebPlugin } from '@capacitor/core';

import type { NoiseMeterPlugin } from './definitions';

export class NoiseMeterWeb extends WebPlugin implements NoiseMeterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
